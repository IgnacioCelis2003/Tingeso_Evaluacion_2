package com.KartingRM.reserva_service.Service;


import com.KartingRM.reserva_service.Entity.ComprobanteEntity;
import com.KartingRM.reserva_service.Entity.ReservaEntity;
import com.KartingRM.reserva_service.Repository.ComprobanteRepository;
import com.KartingRM.reserva_service.Repository.ReservaRepository;
import com.KartingRM.reserva_service.RestTemplateConfig;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ComprobanteService {
    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    @Autowired
    private RestTemplate restTemplate;


    public List<ComprobanteEntity> getComprobantes() {
        return comprobanteRepository.findAll();
    }

    public byte[] generarPDFComprobante(ComprobanteEntity comprobante, ReservaEntity reserva) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("🏁 Comprobante de Reserva KartingRM \n"));
            document.add(new Paragraph("Nombre del Cliente: " + comprobante.getNombreCliente()));
            document.add(new Paragraph("Codigo de la reserva: " + comprobante.getIdReserva()));
            document.add(new Paragraph("Fecha inicio de la reserva: " + reserva.getFechaInicio()));
            document.add(new Paragraph("Fecha fin de la reserva: " + reserva.getFechaFin()));
            switch (reserva.getTipo()){
                case 1:
                    document.add(new Paragraph("Tipo de reserva: 10 vueltas o 10 minutos maximo"));
                    break;
                case 2:
                    document.add(new Paragraph("Tipo de reserva: 15 vueltas o 15 minutos maximo"));
                    break;
                case 3:
                    document.add(new Paragraph("Tipo de reserva: 20 vueltas o 20 minutos maximo"));
                    break;
                default:
                    throw new RuntimeException("Tipo de reserva no válido");
            }
            document.add(new Paragraph("Cantidad de personas: " + reserva.getCantidadPersonas()));
            document.add(new Paragraph("Tarifa Base: $" + comprobante.getTarifa()));
            document.add(new Paragraph("Descuento Aplicado: $" + comprobante.getDescuento()));
            document.add(new Paragraph("Monto con Descuento: $" + comprobante.getMontoDescuento()));
            document.add(new Paragraph("IVA (19%): $" + comprobante.getIVA()));
            document.add(new Paragraph("A continucaión se detalla el monto total a pagar por cada persona:\n"));

            Table tabla = new Table(2);
            tabla.addCell(new Cell().add(new Paragraph("Persona")));
            tabla.addCell(new Cell().add(new Paragraph("Total a Pagar")));
            for (int i = 1; i <= reserva.getCantidadPersonas(); i++) {
                tabla.addCell(new Cell().add(new Paragraph("Persona " + i)));
                tabla.addCell(new Cell().add(new Paragraph("$" + (comprobante.getMontoTotal() / reserva.getCantidadPersonas()))));
            }
            tabla.addCell("Monto total");
            tabla.addCell("$" + comprobante.getMontoTotal());
            document.add(tabla);

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        }
    }

    public void enviarComprobantePorCorreo(String destinatario, byte[] pdfBytes) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

            helper.setTo(destinatario);
            helper.setSubject("🏎️ Comprobante de tu reserva en KartingRM");
            helper.setText("Hola! Adjunto encontrarás el comprobante de tu reserva. ¡Gracias por reservar!");

            helper.addAttachment("Comprobante.pdf", new ByteArrayResource(pdfBytes));

            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage(), e);
        }
    }

    @Transactional
    public ComprobanteEntity crearComprobante(long idReserva, String nombreCliente) {
        ReservaEntity reserva = reservaRepository.findById(idReserva);
        if (reserva == null) {
            throw new RuntimeException("La reserva no existe");
        }
        int tipo = reserva.getTipo();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaInicio = LocalDateTime.parse(reserva.getFechaInicio(), formato);

        DateTimeFormatter formatoMesDia = DateTimeFormatter.ofPattern("MM-dd");
        String fechaInicioMesDia = fechaInicio.format(formatoMesDia);
        List<String> feriados = Arrays.asList("01-01", "05-01", "09-18", "09-19", "12-25");

        float tarifa = restTemplate.getForObject("http://localhost:8079/tarifa/" + tipo, float.class);
        float tarifaEspecial = restTemplate.getForObject("http://localhost:8079/tarifaEspecial/calcular/" + tarifa + "/" + reserva.getFechaInicio(), float.class);

        if(tarifa != tarifaEspecial) {
            tarifa = tarifaEspecial;
        }
        if(reserva.getTarifaEspecial() != 0) {
            tarifa = tarifa + reserva.getTarifaEspecial();
        }

        int cantidadPersonas = reserva.getCantidadPersonas();
        float montoInicial = tarifa * cantidadPersonas;


        //Calculos de descuentos
        List<Float> descuentos = new ArrayList<>();
        List<Float> montosDescuentos = new ArrayList<>();

        //Por cantidad de personas

        float descuentoPorCantidadPersonas = restTemplate.getForObject("http://localhost:8079/descuentoGrupo/" + cantidadPersonas, float.class);

        montosDescuentos.add(montoInicial - (montoInicial * descuentoPorCantidadPersonas));
        descuentos.add(montoInicial * descuentoPorCantidadPersonas);



        //Por cantidad de reservas en el mes

        float descuentoPorReservas = restTemplate.getForObject("http://localhost:8079/clienteFrecuente/descuento/" + reserva.getRut() + "/" + reserva.getFechaInicio(), float.class);

        montosDescuentos.add(montoInicial - (montoInicial * descuentoPorReservas));
        descuentos.add(montoInicial * descuentoPorReservas);



        //Por fecha de nacimiento

        float descuentoPorCumple = restTemplate.getForObject("http://localhost:8079/tarifaEspecial/descuento/" + reserva.getCantidadCumple() + "/" + cantidadPersonas + "/" + tarifa, float.class);

        montosDescuentos.add(montoInicial - descuentoPorCumple);
        descuentos.add(descuentoPorCumple);

        System.out.println(montoInicial);

        // Elegir descuento con mayor monto

        //float montoDescuento = 0;
        //float descuento = 0;

        System.out.println("Montos Descuentos: " + montosDescuentos);
        System.out.println("Descuentos: " + descuentos);


        Collections.sort(montosDescuentos);
        Collections.sort(descuentos);

        float descuento = descuentos.get(descuentos.size() - 1);
        float montoDescuento = montosDescuentos.get(0);



        float IVA = montoDescuento * 0.19f;
        float montoTotal = montoDescuento + IVA;

        ComprobanteEntity comprobante = new ComprobanteEntity(idReserva, nombreCliente);
        comprobante.setTarifa(tarifa);
        comprobante.setDescuento(descuento);
        comprobante.setMontoDescuento(montoDescuento);
        comprobante.setIVA(IVA);
        comprobante.setMontoTotal(montoTotal);

        comprobanteRepository.save(comprobante);

        byte[] pdfBytes = generarPDFComprobante(comprobante, reserva);
        enviarComprobantePorCorreo(reserva.getEmail(), pdfBytes);

        return comprobante;
    }

}

import React, { useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import reservaService from "../Services/reserva.service";
import { format } from "date-fns";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Box,
  useMediaQuery,
  Checkbox,
  FormControlLabel,
  Typography,
} from "@mui/material";

export default function Calendario() {
  const [reservas, setReservas] = useState([]);
  const [eventos, setEventos] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedStartTime, setSelectedStartTime] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [needsTarifaEspecial, setNeedsTarifaEspecial] = useState(false);
  const [reservaForm, setReservaForm] = useState({
    nombre: "",
    apellidoPaterno: "",
    apellidoMaterno: "",
    rut: "",
    fechaInicio: "",
    tipo: 1,
    cantidadPersonas: 1,
    cantidadCumple: 0,
    email: "",
    tarifaEspecial: 0,
  });

  const isMobile = useMediaQuery((theme) => theme.breakpoints.down("sm"));

  function transformarReservaAEvento(reserva) {
    return {
      id: reserva.id,
      title: `${reserva.nombre} ${reserva.apellidoPaterno}`,
      start: reserva.fechaInicio,
      end: reserva.fechaFin,
      extendedProps: {
        rut: reserva.rut,
        tipo: reserva.tipo,
        cantidadPersonas: reserva.cantidadPersonas,
        cantidadCumple: reserva.cantidadCumple,
        email: reserva.email,
        tarifaEspecial: reserva.tarifaEspecial,
        nombreCompleto: `${reserva.nombre} ${reserva.apellidoPaterno} ${reserva.apellidoMaterno}`,
      },
    };
  }

  async function fetchReservas() {
    try {
      const response = await reservaService.getReservas();
      setReservas(response.data);
      const eventosTransformados = response.data.map(transformarReservaAEvento);
      setEventos(eventosTransformados);
    } catch (error) {
      console.error("Error fetching reservas:", error);
    }
  }

  useEffect(() => {
    fetchReservas();
  }, []);

  async function handleSubmit(e) {
    e.preventDefault();
    setIsSubmitting(true);
    try {
      const response = await reservaService.crearReserva(reservaForm);
      setShowModal(false);
      setNeedsTarifaEspecial(false);
      fetchReservas();
      alert("Reserva creada exitosamente!");
      setReservaForm({
        nombre: "",
        apellidoPaterno: "",
        apellidoMaterno: "",
        rut: "",
        fechaInicio: "",
        tipo: 1,
        cantidadPersonas: 1,
        cantidadCumple: 0,
        email: "",
        tarifaEspecial: 0,
      });
    } catch (error) {
      console.error("Error creating reserva:", error);
      alert("Error al crear la reserva: " + error.message);
    } finally {
      setIsSubmitting(false);
    }
  }

  const handleDateSelect = (selectInfo) => {
    const startDate = new Date(selectInfo.startStr);
    const formattedStartTime = format(startDate, "yyyy-MM-dd'T'HH:mm");
    setSelectedStartTime(formattedStartTime);
    setReservaForm({ ...reservaForm, fechaInicio: formattedStartTime });
    setShowModal(true);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setReservaForm({ ...reservaForm, [name]: value });
  };

  const handleCheckboxChange = (e) => {
    setNeedsTarifaEspecial(e.target.checked);
    if (!e.target.checked) {
      setReservaForm({ ...reservaForm, tarifaEspecial: 0 });
    }
  };

  return (
    <>
      <FullCalendar
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        initialView={isMobile ? "timeGridDay" : "timeGridWeek"}
        headerToolbar={{
          left: "prev,next today",
          center: "title",
          right: isMobile
            ? "timeGridDay,timeGridWeek"
            : "dayGridMonth,timeGridWeek,timeGridDay",
        }}
        slotMinTime={"10:00:00"}
        slotMaxTime={"22:00:00"}
        events={eventos}
        selectable={true}
        height="auto"
        select={handleDateSelect}
        allDaySlot={false}
        eventClick={(info) => {
          alert(
            `Evento: ${info.event.title}\nRUT: ${info.event.extendedProps.rut}\nTipo: ${info.event.extendedProps.tipo}\nCantidad de personas: ${info.event.extendedProps.cantidadPersonas}\nCantidad de cumpleañeros: ${info.event.extendedProps.cantidadCumple}\nEmail: ${info.event.extendedProps.email}\nTarifa Especial: ${info.event.extendedProps.tarifaEspecial}`
          );
        }}
        eventBackgroundColor="#1976d2"
        eventBorderColor="#1565c0"
        eventTextColor="#fff"
        timeZone="local"
        slotLabelFormat={{
          hour: "numeric",
          minute: "2-digit",
          hour12: true,
        }}
        eventTimeFormat={{
          hour: "numeric",
          minute: "2-digit",
          hour12: true,
        }}
      />
      <Dialog
        open={showModal}
        onClose={() => !isSubmitting && setShowModal(false)}
        maxWidth="sm"
        fullWidth
        fullScreen={isMobile}
        disableEscapeKeyDown={isSubmitting}
      >
        <DialogTitle sx={{ fontSize: { xs: "1.25rem", sm: "1.5rem" } }}>
          Crear Nueva Reserva
        </DialogTitle>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                gap: 2,
              }}
            >
              <TextField
                label="Nombre"
                name="nombre"
                value={reservaForm.nombre}
                onChange={handleInputChange}
                required
                fullWidth
                disabled={isSubmitting}
              />
              <TextField
                label="Apellido Paterno"
                name="apellidoPaterno"
                value={reservaForm.apellidoPaterno}
                onChange={handleInputChange}
                required
                fullWidth
                disabled={isSubmitting}
              />
              <TextField
                label="Apellido Materno"
                name="apellidoMaterno"
                value={reservaForm.apellidoMaterno}
                onChange={handleInputChange}
                required
                fullWidth
                disabled={isSubmitting}
              />
              <TextField
                label="RUT"
                name="rut"
                value={reservaForm.rut}
                onChange={handleInputChange}
                required
                fullWidth
                disabled={isSubmitting}
              />
              <TextField
                label="Fecha Inicio"
                name="fechaInicio"
                type="datetime-local"
                value={reservaForm.fechaInicio.slice(0, 16)}
                onChange={handleInputChange}
                required
                fullWidth
                InputLabelProps={{ shrink: true }}
                disabled={isSubmitting}
              />
              <FormControl fullWidth>
                <InputLabel>Tipo</InputLabel>
                <Select
                  name="tipo"
                  value={reservaForm.tipo}
                  onChange={handleInputChange}
                  label="Tipo"
                  disabled={isSubmitting}
                >
                  <MenuItem value={1}>10 minutos o 10 vueltas</MenuItem>
                  <MenuItem value={2}>15 minutos o 15 vueltas</MenuItem>
                  <MenuItem value={3}>20 minutos o 20 vueltas</MenuItem>
                </Select>
              </FormControl>
              <TextField
                label="Cantidad Personas"
                name="cantidadPersonas"
                type="number"
                value={reservaForm.cantidadPersonas}
                onChange={handleInputChange}
                required
                fullWidth
                inputProps={{ min: 1, max: 15 }}
                disabled={isSubmitting}
              />
              <TextField
                label="Cantidad Cumpleañeros"
                name="cantidadCumple"
                type="number"
                value={reservaForm.cantidadCumple}
                onChange={handleInputChange}
                required
                fullWidth
                inputProps={{ min: 0, max: 15 }}
                disabled={isSubmitting}
              />
              <TextField
                label="Email"
                name="email"
                type="email"
                value={reservaForm.email}
                onChange={handleInputChange}
                required
                fullWidth
                disabled={isSubmitting}
              />
              <FormControlLabel
                control={
                  <Checkbox
                    checked={needsTarifaEspecial}
                    onChange={handleCheckboxChange}
                    disabled={isSubmitting}
                  />
                }
                label="¿Necesita tarifa especial?"
              />
              {needsTarifaEspecial && (
                <>
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{ fontSize: { xs: "0.75rem", sm: "0.875rem" } }}
                  >
                    Este monto se sumará a la tarifa base.
                  </Typography>
                  <TextField
                    label="Tarifa Especial"
                    name="tarifaEspecial"
                    type="number"
                    value={reservaForm.tarifaEspecial}
                    onChange={handleInputChange}
                    required={needsTarifaEspecial}
                    fullWidth
                    inputProps={{ step: "0.01", min: "0" }}
                    disabled={isSubmitting}
                  />
                </>
              )}
            </Box>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setShowModal(false)} disabled={isSubmitting}>
              Cancelar
            </Button>
            <Button type="submit" variant="contained" disabled={isSubmitting}>
              {isSubmitting ? "Creando..." : "Crear Reserva"}
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </>
  );
}
import React, { useState } from "react";
import {
  Box,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
} from "@mui/material";
import comprobanteService from "../Services/comprobante.service"; // Ajusta la ruta según tu estructura

export default function Reportes() {
  const [reporteTipo, setReporteTipo] = useState("tipo"); // "tipo" o "cantidad"
  const [inicio, setInicio] = useState(""); // Formato: "yyyy-MM"
  const [fin, setFin] = useState(""); // Formato: "yyyy-MM"
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  const meses = [
    "Enero",
    "Febrero",
    "Marzo",
    "Abril",
    "Mayo",
    "Junio",
    "Julio",
    "Agosto",
    "Septiembre",
    "Octubre",
    "Noviembre",
    "Diciembre",
  ];

  const tipos = ["Tipo 1 (30 min)", "Tipo 2 (35 min)", "Tipo 3 (40 min)"];
  const cantidades = ["1-2 personas", "3-5 personas", "6-10 personas", "11-15 personas"];

  const handleFetchReporte = async () => {
    setError(null);
    try {
      let response;
      if (reporteTipo === "tipo") {
        response = await comprobanteService.getReportePorTipo(inicio, fin);
      } else {
        response = await comprobanteService.getReportePorCantidad(inicio, fin);
      }
      setData(response.data);
    } catch (err) {
      setError(err.response?.data?.message || "Error al obtener el reporte");
      setData(null);
    }
  };

  const calcularTotalFila = (fila) => fila.reduce((sum, val) => sum + val, 0);
  const calcularTotalColumna = (colIndex) =>
    data?.reduce((sum, fila) => sum + fila[colIndex], 0) || 0;

  const getMesesRango = () => {
    if (!inicio || !fin) return [];
    const inicioYearMonth = new Date(inicio);
    const finYearMonth = new Date(fin);
    const inicioMes = inicioYearMonth.getMonth();
    const finMes = finYearMonth.getMonth();
    return meses.slice(inicioMes, finMes + 1);
  };

  return (
    <Box sx={{ p: { xs: 2, sm: 4 }, maxWidth: 1200, mx: "auto"}}>
      <Typography
        variant="h4"
        gutterBottom
        sx={{ fontSize: { xs: "1.5rem", sm: "2rem" } }}
      >
        Reporte de Reservas
      </Typography>

      <Box
        sx={{
          display: "flex",
          flexDirection: { xs: "column", sm: "row" }, // Apilar en móviles, fila en escritorio
          gap: 2,
          mb: 4,
        }}
      >
        <FormControl sx={{ width: { xs: "100%", sm: 200 } }}>
          <InputLabel>Tipo de Reporte</InputLabel>
          <Select
            value={reporteTipo}
            onChange={(e) => setReporteTipo(e.target.value)}
            label="Tipo de Reporte"
          >
            <MenuItem value="tipo">Por Tipo</MenuItem>
            <MenuItem value="cantidad">Por Cantidad</MenuItem>
          </Select>
        </FormControl>

        <TextField
          label="Mes Inicio"
          type="month"
          value={inicio}
          onChange={(e) => setInicio(e.target.value)}
          InputLabelProps={{ shrink: true }}
          sx={{ width: { xs: "100%", sm: 200 } }}
        />
        <TextField
          label="Mes Fin"
          type="month"
          value={fin}
          onChange={(e) => setFin(e.target.value)}
          InputLabelProps={{ shrink: true }}
          sx={{ width: { xs: "100%", sm: 200 } }}
        />
        <Button
          variant="contained"
          onClick={handleFetchReporte}
          disabled={!inicio || !fin}
          sx={{ width: { xs: "100%", sm: "auto" } }}
        >
          Generar Reporte
        </Button>
      </Box>

      {error && (
        <Typography color="error" sx={{ mb: 2, fontSize: { xs: "0.875rem", sm: "1rem" } }}>
          {error}
        </Typography>
      )}

      {data && (
        <TableContainer component={Paper} sx={{ overflowX: "auto" }}>
          <Table sx={{ minWidth: { xs: 300, sm: 650 } }}>
            <TableHead>
              <TableRow>
                <TableCell sx={{ fontWeight: "bold", fontSize: { xs: "0.75rem", sm: "1rem" } }}>
                  {reporteTipo === "tipo" ? "Tipo" : "Cantidad"}
                </TableCell>
                {getMesesRango().map((mes, index) => (
                  <TableCell
                    key={index}
                    align="right"
                    sx={{ fontWeight: "bold", fontSize: { xs: "0.75rem", sm: "1rem" } }}
                  >
                    {mes}
                  </TableCell>
                ))}
                <TableCell
                  align="right"
                  sx={{ fontWeight: "bold", fontSize: { xs: "0.75rem", sm: "1rem" } }}
                >
                  Total
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {data.map((fila, index) => (
                <TableRow key={index}>
                  <TableCell sx={{ fontSize: { xs: "0.75rem", sm: "1rem" } }}>
                    {reporteTipo === "tipo" ? tipos[index] : cantidades[index]}
                  </TableCell>
                  {fila.map((valor, colIndex) => (
                    <TableCell
                      key={colIndex}
                      align="right"
                      sx={{ fontSize: { xs: "0.75rem", sm: "1rem" } }}
                    >
                      {valor.toFixed(2)}
                    </TableCell>
                  ))}
                  <TableCell
                    align="right"
                    sx={{ fontWeight: "bold", fontSize: { xs: "0.75rem", sm: "1rem" } }}
                  >
                    {calcularTotalFila(fila).toFixed(2)}
                  </TableCell>
                </TableRow>
              ))}
              <TableRow>
                <TableCell sx={{ fontWeight: "bold", fontSize: { xs: "0.75rem", sm: "1rem" } }}>
                  Total
                </TableCell>
                {getMesesRango().map((_, index) => (
                  <TableCell
                    key={index}
                    align="right"
                    sx={{ fontWeight: "bold", fontSize: { xs: "0.75rem", sm: "1rem" } }}
                  >
                    {calcularTotalColumna(index).toFixed(2)}
                  </TableCell>
                ))}
                <TableCell
                  align="right"
                  sx={{ fontWeight: "bold", fontSize: { xs: "0.75rem", sm: "1rem" } }}
                >
                  {data
                    .reduce((sum, fila) => sum + calcularTotalFila(fila), 0)
                    .toFixed(2)}
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Box>
  );
}
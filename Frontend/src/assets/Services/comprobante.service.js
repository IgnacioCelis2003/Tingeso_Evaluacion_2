import axios from "axios";

const API_URL = `http://localhost:8079/reporte`;

function getReportePorTipo(inicio, fin) {
    return axios.get(`${API_URL}/tipo/${inicio}/${fin}`);
}

function getReportePorCantidad(inicio, fin) {
    return axios.get(`${API_URL}/cantidad/${inicio}/${fin}`);
}

export default {
    getReportePorTipo,
    getReportePorCantidad
};
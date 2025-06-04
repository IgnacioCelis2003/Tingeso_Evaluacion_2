import axios from "axios";

const API_URL = `http://localhost:8079/reserva`;

function crearReserva(reserva) {
  return axios.post(API_URL + "/crear", reserva);
}

function getReservas() {
  return axios.get(API_URL + "/reservas");
}

export default {
  crearReserva,
  getReservas
};
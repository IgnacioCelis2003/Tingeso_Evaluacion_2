import axios from "axios";

const API_URL = `http://localhost:8079/rack`;

function obtenerReservas() {
  return axios.get(API_URL + "/reservas");
}

export default {
  obtenerReservas
};
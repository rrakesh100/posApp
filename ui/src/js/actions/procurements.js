import axios from 'axios';


export function addProcurement (procurement) {
  return axios.post('http://localhost:8080/v1/procurements',  {...procurement});
}

export function allProcurements() {
  return axios.get('http://localhost:8080/v1/procurements/');
}

export function fetchProcurement(procurementId) {
  return axios.get('http://localhost:8080/v1/procurements/' + procurementId);
}

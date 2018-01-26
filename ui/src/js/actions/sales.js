import axios from 'axios';


export function createBill (sale) {
  return axios.post('http://localhost:8080/v1/sales',  {...sale});
}

export function allSales() {
  return axios.get('http://localhost:8080/v1/sales');
}

export function fetchSale(saleId) {
  return axios.get('http://localhost:8080/v1/sales/' + saleId);
}

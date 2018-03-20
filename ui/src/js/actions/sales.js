import axios from 'axios';


export function createBill (sale) {
  return axios.post('http://35.154.199.100:8080/v1/sales',  {...sale});
}

export function allSales() {
  return axios.get('http://35.154.199.100:8080/v1/sales');
}

export function fetchSale(saleId) {
  return axios.get('http://35.154.199.100:8080/v1/sales/' + saleId);
}

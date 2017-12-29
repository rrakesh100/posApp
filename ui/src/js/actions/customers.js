import axios from 'axios';


export function createCustomer(employee) {
  return axios.post('http://localhost:8080/v1/items',  {...employee});
}

export function editCustomer(employee) {
  return axios.put('http://localhost:8080/v1/items',  {...employee});
}

export function deleteCustomer(employee) {
  return axios.delete('http://localhost:8080/v1/items'+ id);
}


export function getCustomer(id) {
  return axios.get('http://localhost:8080/v1/items/' + id);
}


export function allCustomers() {
  return axios.get('http://localhost:8080/v1/items/');
}

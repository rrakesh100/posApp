import axios from 'axios';


export function createCustomer(customer) {
  return axios.post('http://localhost:8080/v1/customers',  {...customer});
}

export function editCustomer(customer) {
  return axios.put('http://localhost:8080/v1/customers',  {...customer});
}

export function deleteCustomer() {
  return axios.delete('http://localhost:8080/v1/customers'+ id);
}


export function getCustomer(id) {
  return axios.get('http://localhost:8080/v1/customers/' + id);
}


export function allCustomers() {
  return axios.get('http://localhost:8080/v1/customers/');
}

export function getCustomersWithPattern(pattern) {
  return axios.get('http://localhost:8080/v1/filteredCustomers?searchPattern=' + pattern);
}

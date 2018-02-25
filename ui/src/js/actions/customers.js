
import axios from 'axios';


export function createCustomer(customer) {
  return axios.post('http://192.168.31.196:8080/v1/customers',  {...customer});
}

export function editCustomer(customer) {
  return axios.put('http://192.168.31.196:8080/v1/customers',  {...customer});
}

export function deleteCustomer(id) {
  return axios.delete('http://192.168.31.196:8080/v1/customers'+id);
}


export function getCustomer(id) {
  return axios.get('http://192.168.31.196:8080/v1/customers/'+id);
}


export function allCustomers() {
  return axios.get('http://192.168.31.196:8080/v1/customers');
}

export function getCustomersWithPattern(pattern) {
  return axios.get('http://192.168.31.196:8080/v1/filteredCustomers?searchPattern=' + pattern);
}

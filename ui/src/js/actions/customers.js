
import axios from 'axios';


export function createCustomer(customer) {
  return axios.post('http://35.154.199.100:8080/v1/customers',  {...customer});
}

export function editCustomer(customer) {
  return axios.put('http://35.154.199.100:8080/v1/customers',  {...customer});
}

export function deleteCustomer(id) {
  return axios.delete('http://35.154.199.100:8080/v1/customers/'+id);
}


export function getCustomer(id) {
  return axios.get('http://35.154.199.100:8080/v1/customers/'+id);
}


export function allCustomers() {
  return axios.get('http://35.154.199.100:8080/v1/customers');
}

export function getCustomersWithPattern(pattern) {
  return axios.get('http://35.154.199.100:8080/v1/filteredCustomers?searchPattern=' + pattern);
}

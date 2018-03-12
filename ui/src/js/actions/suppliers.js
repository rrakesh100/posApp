import axios from 'axios';

export function createSupplier(supplier) {
  return axios.post('http://35.154.199.100:8080/v1/suppliers', {...supplier});
}

export function editSupplier(supplier) {
  return axios.put('http://35.154.199.100:8080/v1/suppliers', {...supplier});
}

export function deleteSupplier(id) {
  return axios.delete('http://35.154.199.100:8080/v1/suppliers/'+ id);
}

export function getSupplier(id) {
  return axios.get('http://35.154.199.100:8080/v1/suppliers/'+id);
}

export function allSuppliers() {
  return axios.get('http://35.154.199.100:8080/v1/suppliers');
}

export function getSuppliersWithPattern(pattern) {
  return axios.get('http://35.154.199.100:8080/v1/filteredSuppliers?searchPattern=' + pattern);
}

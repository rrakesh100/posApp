import axios from 'axios';


export function createItem (item) {
  return axios.post('http://localhost:8080/v1/items',  {...item});
}

export function editItem (item) {
  return axios.put('http://localhost:8080/v1/items',  {...item});
}

export function deleteItem (item) {
  return axios.delete('http://localhost:8080/v1/items'+ id);
}


export function getItem (id) {
  return axios.get('http://localhost:8080/v1/items/' + id);
}


export function allItems() {
  return axios.get('http://localhost:8080/v1/items/');
}

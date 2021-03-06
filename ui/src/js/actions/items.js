import axios from 'axios';


export function createItem (item) {
  return axios.post('http://35.154.199.100:8080/v1/items',  {...item});
}

export function editItem (item) {
  return axios.put('http://35.154.199.100:8080/v1/items',  {...item});
}

export function deleteItem (itemId) {
  return axios.delete('http://35.154.199.100:8080/v1/items/'+ itemId);
}


export function getItem (id) {
  return axios.get('http://35.154.199.100:8080/v1/items/' + id);
}


export function allItems() {
  return axios.get('http://35.154.199.100:8080/v1/items/');
}

export function getFilteredItems(searchPattern) {
  return axios.get('http://35.154.199.100:8080/v1/filteredItems?searchPattern=' + searchPattern);
}

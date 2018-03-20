import * as employeeActions from '../api/employees'
import $ from 'jquery';
import { EMPLOYEES_LOAD, EMPLOYEES_UNLOAD } from '../actions';
import axios from 'axios';


export function createEmployee(employee) {
  return axios.post('http://35.154.199.100:8080/v1/items',  {...employee});
}

export function editEmployee(employee) {
  return axios.put('http://35.154.199.100:8080/v1/items',  {...employee});
}

export function deleteEmployee(employee) {
  return axios.delete('http://35.154.199.100:8080/v1/items'+ id);
}


export function getEmployee(id) {
  return axios.get('http://35.154.199.100:8080/v1/items/' + id);
}


export function allEmployees() {
  return axios.get('http://35.154.199.100:8080/v1/items/');
}

//
// export function unloadEmployees() {
//   return employeeActions.getAllEmployees();
// }


// export function loadEmployees() {
//    return  dispatch => (
//      $.ajax({
//            url:'http://35.154.199.100:8080/v1/items',
//            success: (data) => {
//              dispatch({ type: EMPLOYEES_LOAD, data });
//            }
//      })
//      );
// }

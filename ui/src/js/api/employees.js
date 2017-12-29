import $ from 'jquery';

export function getAllEmployees(){
  $.ajax({
        url:'http://localhost:8080/v1/items',
        success: (data) => {
          console.log(data);
          return data;
        }
  })

}

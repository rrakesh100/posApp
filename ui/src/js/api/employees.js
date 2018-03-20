import $ from 'jquery';

export function getAllEmployees(){
  $.ajax({
        url:'http://35.154.199.100:8080/v1/items',
        success: (data) => {
          console.log(data);
          return data;
        }
  })

}

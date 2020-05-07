let oID =0

$(document).ready(function() {
  oID = $("#oID").text();
  $('#submitBtn').submit(function(e) {
   e.preventDefault();
   $('#exampleModal').modal('toggle');
   return false;
  });
  getAdminList();
});

function getAdminList() {
    $.get(`/adminListData`, function(res){
      res = JSON.parse(res);
      console.log(res);
      res = res.data;
      for(let i = 0; i < res.length; i++ ){
        $("#listOfAdmins").append(`<tr>
          <th scope="row">${i+1}</th>
          <td>${res[i].aID}</td>
          <td>${res[i].aFirstName}</td>
          <td>${res[i].aLastName}</td>
        </tr>`);
      }
    });
}

// <td>${res[i].aDepartment}</td>

let oID = 0

$(document).ready(function() {
  // oID = $("#oID").text();
  $('#submitBtn').submit(function(e) {
   e.preventDefault();
   $('#exampleModal').modal('toggle');
   return false;
  });

  getStudentList();
});

function getStudentList() {
    $.get(`/studentListData`, function(res) {
      res = JSON.parse(res);
      console.log(res.status);
      if (res.status == "success") {
        res = res.data;
        for(let i = 0; i < res.length; i++) {
          $("#listOfStudents").append(`<tr>
            <th scope="row">${i+1}</th>
            <td>${res[i].sID}</td>
            <td>${res[i].sFirstName}</td>
            <td>${res[i].sLastName}</td>
          </tr>`);
        }
      } else {
        alert("Failed to connect");
      }

    });
}

// <td>${res[i].sDepartment}</td>
//<td><a class="btn btn-link" href="/transcript/${iID}/${res[i].sID}">View Transcript</a></td>

let oID = 0
var toEnroll = 0;

$(document).ready(function() {
  // oID = $("#oID").text();
  $('#submitBtn').submit(function(e) {
   e.preventDefault();
   $('#exampleModal').modal('toggle');
   return false;
  });

  addEnrolledStudents();

  getToEnrollStudents();
});

function addEnrolledStudents() {
    $.get(`/enrolledStudentsData/${$("#cID").text()}`, function(res) {
      res = JSON.parse(res);
      console.log(res);
      res = res.data;
      for(let i = 0; i < res.length; i++) {
        $("#enrolled").append(`<tr>
                          <th id=${res[i].sID} scope="row">${i+1}</th>
                          <td>${res[i].sID}</td>
                          <td>${res[i].sFirstName}</td>
                          <td>${res[i].sLastName}</td>
                        </tr>`);
      }
    });
}

function getToEnrollStudents() {
  $.get(`/toEnroll/student/${$("#cID").text()}`, function(res){
    console.log(res);
    res = JSON.parse(res);
    if (res.status == "success") {

      res = res.data;
      console.log(res);
      for(let i = 0; i < res.length; i++) {
        $("#allStudentsList").append(`<tr>
             <th scope="row">${res[i].sID}</th>
             <td>${res[i].sName}</td>
             <td><button class="btn btn-success" onClick="enrollStudent(${res[i].sID})">enroll</button></td></td>
           </tr>`);
      }
    } else {
      $("#allStudentsList").append(`<h5> No Students to Invite`);
    }


  });
}

function enrollStudent(sid) {
  $.post( "/enroll/student", { cID : $("#cID").text() , sID : sid},
    function(res){
      res = JSON.parse(res);
      if (res.status == "success") {
        $(`#${sid}`).remove();
      } else {
        alert("Failed to add student");
      }
  });
}

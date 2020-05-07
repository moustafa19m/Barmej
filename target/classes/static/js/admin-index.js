let oID = 0

$(document).ready(function() {
  getPurchasedCourses();
});


function getPurchasedCourses() {
  console.log("hello");
  $.get(`/ownedCourses`, function(res){
    res = JSON.parse(res);
    console.log(res);
    res = res.data;
    for(let i = 0; i < res.length; i++ ){
      $("#coursesHolder").append(`<div id=${res[i].cID} class="col-lg-6" style="margin-bottom: 3%">
        <div class="container col-lg-12" >
          <img src="${res[i].cImgUrl}" style="height: 150px; width: 100%;">
          <br>
          <div class="container" style=" border: 2px solid #5a5a5d;
border-radius: 0px 0px 30px 30px;">
            <h5>${res[i].cTitle}</h5>
            <text><strong>Enrolled Students:</strong> ${res[i].cEnrolled}</text>
            <br>
            <text><strong>Max num of students:</strong> ${res[i].cMaxStudents}</text>
            <br>
            <text><strong>Level:</strong> ${res[i].cLevel} out of 5</text>
            <br>
            <br>
            <div class="text-center">
              <a class="col-lg-6 btn btn-primary" href="/admin/${res[i].cID}">View Course</a>
            </div>
            <br>
          </div>
        </div>
      </div>`);
    }
  });
}

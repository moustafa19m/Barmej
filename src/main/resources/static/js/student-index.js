$(document).ready(function () {
	getEnrolledCourses();
});

function getEnrolledCourses() {
	$.get("/myCourses", function(res) {
		res = JSON.parse(res);
		console.log(res);
		res = res.data;
		console.log(res);
		for (let i = 0; i < res.length; i++) {
			$("#enrolledCourses").append(`
				<br>
				<div id=${res[i].cID} class="col-lg-6" style="margin-bottom: 3%">
                <div class="conatiner col-lg-12" >
                  <img src="images/${res[i].cImgUrl}" style="height: 150px; width: 100%;">
                  <br>
                  <div class="container" style=" border: 2px solid #5a5a5d;
  border-radius: 0px 0px 30px 30px;">
                    <h5>${res[i].cTitle}</h5>
                    <br>
                    <div class="text-center">
                      <a class="img col-lg-5 btn-sm btn-primary" href="/student/${res[i].cID}:1">Go to Course</a>
                    </div>
                    <br>
                    <div class="progress" style="height:15px">
  <div class="progress-bar bg-warning progress-bar-striped" role="progressbar" aria-valuenow="70"
  aria-valuemin="0" aria-valuemax="100" style="width:70%">
    <span class="sr-only">70% Complete</span>
  </div>
</div>
<br>
                  </div>
                </div>
              </div>`);
		}
	});
}

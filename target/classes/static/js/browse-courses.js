let oID = 0


function rando() {
  console.log("got in here");
  $.post( "/recommendedCourses", {
    math: $("#myRange1").val(),
    creative: $("#myRange2").val(),
    advanced: $("#myRange3").val(),
    theoretical: $("#myRange4").val(),
    practical: $("#myRange5").val(),
    problem_solving: $("#myRange6").val(),
    numCourses: $("#numToFind").val()}, function( res ) {
     res = JSON.parse(res);
     console.log(res);
     if (res.status == "success") {
       res = res.data;
       $("#courseHolder").empty();
  		 for(let i = 0; i < res.length; i++) {
  			 $("#courseHolder").append(`<div class="row" style="background-color: #ededed; margin-bottom: 3%" >
              <div class="col-lg-4" >
                <img src="${res[i].cImgUrl}" alt="" style="height: 200px;">
              </div>
              <div class="col-lg-8">
                <h3>${res[i].cTitle}</h3>
                <div class="container row">
                  <a class="btn btn-primary btn-sm" style="margin-right: 5%; color: white">${res[i].cLevel}</a>
                  <a class="btn btn-primary btn-sm" style="margin-right: 5%; color: white"> 10th-11Grade</a>
                  <a class="btn btn-primary btn-sm" style="margin-right: 5%; color: white"> Design</a>
                  <p>As a graduate student from Stanford university, Stever has received a lot of  Diam volutpat commodo sed egestas egestas. Maecenas pharetra convallis posuere morbi. Bibendum enim facilisis gravida neque convallis a cras semper.</p>
                  <a class="btn btn-info btn-sm" style="margin-right: 5%; color: white" href="/course/${res[i].cID}"> Learn More</a>
                </div>
              </div>
            </div>`);
  		 }
     }
	 });
}

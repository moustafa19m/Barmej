var cID = 0;

$(document).ready(function() {
	cID = $("#cID").text();
	$.getJSON(`/admin/json/${cID}`, function(json){

    if (json.length == 0 || json.length == undefined) {
      $("#builder").append(`Course is still being developed`);
    } else {
			json = JSON.parse(json);
			constructBody(json);
  }
	})
});


function constructBody(json) {
  if (json.length == 0) {
    $("#builder").append(`Course is still being developed`);
  } else {
    $("#builder").append(`<div class="pb-2 mt-4 mb-2 border-bottom">
      <h2>${json.title}</h2>
    </div>
    <div style="height: 76vh;">
    <h4>What you will learn:</h4>
    <div id="learningObj">

    </div>

    <div id="plan">
    <h4>Course Plan:</h4>
    <br>

    </div>

    <div id="instructor">

    </div>`);
    for (var key in json.obj) {
      $("#learningObj").append(`<p> ${key}) ${json.obj[key]}</p>
`);
    }


    for (var key in json.plan) {
      console.log(json.plan[key]);
      $("#plan").append(`<h5> ${json.plan[key].topic}</h5>

      ${json.plan[key].description}
      <br><br>`);
    }

    const inst = json.instructor;
    $("#instructor").append(`<h5>About the Educator, ${inst.name}</h5>
      ${inst.about}`);
  }

}

function purchase() {
  console.log("hello");
  $.post(`/purchase`, { cID : cID, hash : getCookie("hash")}, function(res) {
    res = JSON.parse(res);
    console.log(res);
    if (res.status == "success") {
      $('#button').prop('disabled', true);
      $('#button').text("Successfully Added to Dashboard")
    } else if (res.status == "bought") {
			$('#button').text("You have already bought the course")
		}else {
      alert("Failed to get course");
    }
  });
}


function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

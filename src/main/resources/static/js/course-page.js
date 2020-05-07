var introToWebApps = 0;


$(document).ready(function() {
	const cID = $("#cID").text()
	const weekNum = $("#weekNum").text()
	$.getJSON(`/student/json/${cID}`, function(json){
		console.log(cID);
		if (json.length == 0 || json.length == undefined) {
			$("#firstCont").append("Course Under Development");
		} else {
			json = JSON.parse(json);
			console.log(json);
			introToWebApps = json;
			constructBody();
		  constructPage(weekNum);
		}

	})
});

// const introToWebApps = ;
    function constructBody() {
      // Setting the main body of the page up
      $("#firstCont").append(`<div class="pb-2 mt-4 mb-2 border-bottom">
          <h1 id="${introToWebApps[0].title}"
          style="color: #166F41"> ${introToWebApps[0].title} </h1>
        </div>
        <div class="row">
          <div class="col-lg-3" >
          <div class="text-center">
                <img class="img col-lg-8" src="../images/tree.jpg" alt="">
              </div>
            <div id="dropdownHolder" class="dropdown" style="background-color: #ededed">
          </div>
          </div>
           <div id="contentHolder" class="col-lg-9">
           </div>
        </div>`);
}

    function constructPage(currIndex) {
    	$("#contentHolder").empty();
    	$("#dropdownHolder").empty();

    	// Getting the number of objects in the json array, which represent the
		// number of weeks
      const length = introToWebApps.length;

      // Creating a drop-down menu with its modules for each week
      for (let i = 0; i < (length); i++) {

    	  const numOfModsForWeek = Object.keys(introToWebApps[i].modules).length;
    	  var className;

    	  if (i === (currIndex - 1)) {className = "collapse show"} else {className = "collapse collapsed"}

    	  $("#dropdownHolder").append(`<a class="btn-secondary btn dropdown-item active" data-toggle="collapse"
    	   href="#collapseExample${i}"  aria-expanded="true" aria-controls="collapseExample">
                ${introToWebApps[i].week}
              </a>
              <div id="weekHolder">
              <div class="${className}" id="collapseExample${i}" name="collapseOrNot">
                <div class="row">
                  <div class="col-lg-2">
                  </div>
                  <div id="dropdown${i}" class="dropdown col-lg-10">

                  </div>
                </div>
              </div>
              </div>`);

    	  // Getting the dropdown containers for changing clicked weeks to
			// collapse
		  var dropdownWeek = document.getElementById("weekHolder");
		  var dropdownWeeks = document.getElementsByName("collapseOrNot");

		  // Making the clicked module active
		  for (var m = 0; m < dropdownWeeks.length; m++) {
			  dropdownWeeks[m].addEventListener("click", function() {
			    var current = document.getElementsByClassName("collapse show");

			    // Turning off the active functionality of previously active
				// buttons
			    if (current.length > 0) {
			      current[0].className = current[0].className.replace(" show", "collapsed");
			    }

			    // Making the currently clicked button active
			    this.className += " show";
			  });
			}

    	  var count = 0;
    	  // Creating the dropdown options for the modules of the current week
    	  for (let j = 0; j < numOfModsForWeek; j++) {
    		  count++;
    		  $("#dropdown"+i).append(`<a id="Mod${j}Week${i}"
    		  class="dropdown-item module" href="#${introToWebApps[i].modules[j+1][0]}">
    		  ${introToWebApps[i].modules[j+1][0]}</a>`);

    		  // Getting the dropdown containers for changing clicked modules
				// to active
    		  if (i === (currIndex - 1)) {
    		  var dropdownCont = document.getElementById("dropdown"+(currIndex - 1));
    		  var dropdownElems = dropdownCont.getElementsByClassName("dropdown-item module");

    		  // Making the clicked module active
    		  for (var m = 0; m < dropdownElems.length; m++) {
    			  dropdownElems[m].addEventListener("click", function() {
    			    var current = document.getElementsByClassName("dropdown-item module active");

    			    // Turning off the active functionality of previously active
					// buttons
    			    if (current.length > 0) {
    			      current[0].className = current[0].className.replace(" active", "");
    			    }

    			    // Making the currently clicked button active
    			    this.className += " active";
    			  });
    			}
    	  }
    	  }
      }

	  $("#contentHolder").append(`
	    	  <br><h1 id="${introToWebApps[currIndex - 1].week}" style="color: black">
	    	                	  ${introToWebApps[currIndex - 1].week} </h1>`);

	  const numMods = Object.keys(introToWebApps[currIndex - 1].modules).length;

	  for (let j = 0; j < numMods; j++) {

		  $("#contentHolder").append(`<br>
            	  <h3 id="${introToWebApps[currIndex - 1].modules[j+1][0]}" style="color: #5C6661">
            	  ${introToWebApps[currIndex - 1].modules[j+1][0]} </h3>`);

		  for (courseContent in introToWebApps[currIndex - 1].modules[j+1]) {
			  console.log(courseContent.substring(1,4));
			  if (courseContent.substring(0,4) === "text") {
	    		  $("#contentHolder").append(
	    				  `<p> ${introToWebApps[currIndex - 1].modules[j+1][courseContent]} </p>`);
			  }

			  if (courseContent.substring(0,5) === "video") {
				  $("#contentHolder").append(
						  `<br><p> Here is a YouTube video containing more information about this topic: </p>
							<iframe class="align-self-stretch embed-responsive-item"
							src="${introToWebApps[currIndex - 1].modules[j+1][courseContent]}"></iframe>
				  <br>`);
			  }

			  if (courseContent.substring(0,5) === "image") {
				  $("#contentHolder").append(`<img class="img col-lg-8"
				  src="${introToWebApps[currIndex - 1].modules[j+1][courseContent]}" alt="" align="right">`);
			  }

			  if (courseContent.substring(0,10) === "assignment") {
				  $("#contentHolder").append(`<a href="${introToWebApps[currIndex - 1].modules[j+1][courseContent]}"
				  onclick="ideBuilder(currIndex - 1)" id="prev"
	              class="btn btn-assignment">Go to Assignment</a>
	              <br><br>`);
			  }
	  }
	  }

	  //const length = introToWebApps.length;

	  if ((currIndex - 1) === 0) {
		  $("#contentHolder").append(`<br>
				  	<a href="${introToWebApps[currIndex - 1].prev}" id="prev"
					  class="btn btn-back btn-lg">Back to Dashboard</a>
					  <a href="${introToWebApps[currIndex - 1].nextWeek}"
					  id="nextWeek"
					  class="btn btn-fwd btn-lg" style="float:right">Next Week</a>
					  <br><br>`);
	  } else if ((currIndex - 1) === (length - 1)) {
		  $("#contentHolder").append(`<br>
				  	<a href="${introToWebApps[currIndex - 1].prev}"
				  	 id="prev"
					  class="btn btn-back btn-lg">Previous Week</a>
					  <a href="${introToWebApps[currIndex - 1].nextWeek}" id="prev"
					  class="btn btn-fwd btn-lg" style="float:right">Back to Dashboard</a>
					  <br><br>`);
	  } else {
		  $("#contentHolder").append(`<br>
				  	<a href="${introToWebApps[currIndex - 1].prev}"
				  	 id="prev"
					  class="btn btn-back btn-lg">Previous Week</a>
					  <a href="${introToWebApps[currIndex - 1].nextWeek}"
					  " id="nextWeek"
					  class="btn btn-fwd btn-lg" style="float:right">Next Week </a>
					  <br><br>`);
	  }
    }

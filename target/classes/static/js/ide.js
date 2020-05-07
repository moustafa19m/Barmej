////var editor;
////$(document).ready(function() {
////  var code = $(".codemirror-textarea")[0];
////  editor = CodeMirror.fromTextArea(code, {
////    lineNumbers: true,
////    lineWrapping: true,
////    smartIndent: true,
////    autofocus: true,
////    spellcheck: true,
////    viewportMargin: 20,
////    autoCloseTags: true,
////    dragDrop: true,
////    theme: $("#theme").children("option:selected").val(),
////    mode: $("#language").children("option:selected").val(),
////    indentUnit: $("#indent").children("option:selected").val()
////  });
////  CodeMirror.matchingBracket = true;
////});
////
////
////function updateIDE() {
////  CodeMirror.optionChange({theme: $("#theme").children("option:selected").val(),
////  mode: $("#language").children("option:selected").val(),
////  indentUnit: $("#indent").children("option:selected").val()});
////  console.log($("#language").children("option:selected").val());
////  // CodeMirror.fromTextArea($(".codemirror-textarea")[0], {
////  //   lineNumbers: true,
////  //   lineWrapping: true,
////  //   smartIndent: true,
////  //   autoCloseTags: true,
////  //   autoCloseBrackets: true,
////  //   ,
////  //   autofocus: true,
////  //   spellcheck: true,
////  //   viewportMargin: 20,
////  //   dragDrop: true
////  // });
////
////}
//
//$(document).ready(function() {
//  constructBody();
//  constructPage(0);
//});
//
//const week1IDE = [
//	{
//		title : "Problem Description 1",
//		week : "Week 1",
//		text : "Using the language C1, code a program to find the first 7 numbers in the fibonacci sequence",
//		test: {
//			code : "This is the code the student should have typed in",
//			output : "This is the expected output"
//		}
//	},
//
//	{
//		title : "Problem Description 2",
//		week : "Week 2",
//		text : "Using the language Java2, code a program to find the first 7 numbers in the fibonacci sequence",
//		test : {
//			code : "This is the code the student should have typed in",
//			output : "This is the expected output"
//		}
//	},
//
//	{
//		title : "Problem Description 3",
//		week : "Week 3",
//		text : "Using the language Python3, code a program to find the first 7 numbers in the fibonacci sequence",
//		test : {
//			code : "This is the code the student should have typed in",
//			output : "This is the expected output"
//		}
//	}
//];
//
//function construct(index) {
//	$("#contentHolder").append(` <div class="pb-2 mt-4 mb-2 border-bottom">
//              <h1 style="font-size:30px;"><b>Problem Description</b></h1>
//            </div>
//              <h2 style="font-size:23px;"><strong>WEEK 1</strong></h2>
//            <div class="overflow-auto" style="height: 76vh;">
//              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Egestas quis ipsum suspendisse ultrices gravida dictum fusce ut. Volutpat sed cras ornare arcu dui vivamus arcu felis. Vulputate mi sit amet mauris. Mattis vulputate enim nulla aliquet porttitor lacus luctus accumsan. Laoreet id donec ultrices tincidunt arcu. Suspendisse potenti nullam ac tortor. Convallis convallis tellus id interdum velit laoreet id. Scelerisque fermentum dui faucibus in ornare quam viverra. Ac turpis egestas integer eget aliquet.
//              <br>
//              <br>
//              Non sodales neque sodales ut. Imperdiet dui accumsan sit amet nulla facilisi morbi. Sit amet dictum sit amet justo donec enim. Amet aliquam id diam maecenas. Mattis nunc sed blandit libero volutpat sed cras ornare arcu. Nisl purus in mollis nunc sed id semper. Diam maecenas ultricies mi eget mauris pharetra et ultrices. Bibendum ut tristique et egestas quis ipsum. Velit dignissim sodales ut eu sem integer vitae. Placerat vestibulum lectus mauris ultrices eros in.
//              <br>
//              <br>
//              Et malesuada fames ac turpis egestas maecenas. Libero nunc consequat interdum varius sit amet mattis vulputate. Id nibh tortor id aliquet lectus proin nibh nisl. Tincidunt lobortis feugiat vivamus at augue eget. Feugiat sed lectus vestibulum mattis ullamcorper velit sed. Blandit turpis cursus in hac. Lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit. Pellentesque id nibh tortor id aliquet lectus proin. Elementum sagittis vitae et leo duis ut diam. Id cursus metus aliquam eleifend mi in nulla posuere sollicitudin. Viverra orci sagittis eu volutpat odio. Egestas tellus rutrum tellus pellentesque eu tincidunt. Sed augue lacus viverra vitae congue eu consequat. Iaculis nunc sed augue lacus viverra vitae congue eu. Leo integer malesuada nunc vel risus commodo viverra maecenas. Cras tincidunt lobortis feugiat vivamus at augue eget arcu. Hendrerit gravida rutrum quisque non tellus. Adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus urna. Mi bibendum neque egestas congue quisque egestas diam.
//              <br>
//              <br>
//              Amet nisl suscipit adipiscing bibendum. Eros donec ac odio tempor orci. Suspendisse interdum consectetur libero id faucibus nisl tincidunt eget nullam. Sit amet nisl purus in mollis nunc sed id semper. A cras semper auctor neque vitae tempus quam pellentesque. Ut lectus arcu bibendum at. Eu ultrices vitae auctor eu augue ut lectus arcu bibendum. Sagittis purus sit amet volutpat consequat. Venenatis lectus magna fringilla urna porttitor rhoncus. Commodo elit at imperdiet dui accumsan sit. Enim nec dui nunc mattis enim. Sit amet justo donec enim diam vulputate ut pharetra sit. Vitae elementum curabitur vitae nunc sed velit dignissim sodales ut.
//              <br>
//              <br>
//              Aliquam etiam erat velit scelerisque in dictum. Diam volutpat commodo sed egestas egestas. Maecenas pharetra convallis posuere morbi. Bibendum enim facilisis gravida neque convallis a cras semper. Non consectetur a erat nam. Fames ac turpis egestas sed tempus urna et pharetra. Platea dictumst vestibulum rhoncus est pellentesque elit ullamcorper. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Est velit egestas dui id. Sit amet risus nullam eget felis. Lobortis mattis aliquam faucibus purus in massa. Augue neque gravida in fermentum et sollicitudin. Quam vulputate dignissim suspendisse in est. Dignissim cras tincidunt lobortis feugiat. A diam sollicitudin tempor id eu nisl nunc mi ipsum. Dictum sit amet justo donec enim diam vulputate ut pharetra. Tortor dignissim convallis aenean et.</p>
//            </div>`);
//}
//
//var week1IDE = 0;
//
//
//$(document).ready(function() {
//	const codeProblemID = $("#codeProblemID").text()
//	$.getJSON(`/json/${codeProblemID}`, function(json){
//		json = JSON.parse(json);
//		week1IDE = json;
//		ideBuilder();
//	})
//});

var week1IDE = 0;

$(document).ready(function() {
	const codeProblemID = $("#codeProblemID").text()
	const cID = $("#cID").text()
	const weekNum = $("#weekNum").text()
	$.getJSON(`/json/${cID}:${weekNum}`, function(json){
		json = JSON.parse(json);
//		console.log(json);
		week1IDE = json;
//		constructBody();
//	  constructPage(0);
		ideBuilder();
	})
});

function ideBuilder() {
	$("#ideContentHolder").append(`<div class="pb-2 mt-4 mb-2 border-bottom">
          <h1 style="font-size:30px;"><b>${week1IDE[0].title}</b></h1>
        </div>
          <h2 style="font-size:23px;"><strong>${week1IDE[0].week} </strong></h2>
        <div class="overflow-auto" style="height: 76vh;">
          <p>${week1IDE[0].text}</p>
        </div>`);

}































$(document).ready(function() {
  getQuizQuestions();
});

////Way #1 of doing getQuizQuestion(), assuming each question has 3 answer choices
//function getQuizQuestions() {
//	  $.get(`           `, function(res) {
//	    for(i in res){
//	      $("#questionHolder").append(`<br>
//            <p style="color: #5C6661"><strong> i </strong></p>
//            
// <input type="radio" name="neighInputType" value="neighCoord" id="neighCoord" required>
//    <label for="neighCoord"> res.i[0] </label>
//<br>
// <input type="radio" name="neighInputType" value="neighCoord" id="neighCoord" required>
//    <label for="neighCoord"> res.i[1] </label>
//    <br>
//     <input type="radio" name="neighInputType" value="neighCoord" id="neighCoord" required>
//    <label for="neighCoord"> res.i[2] </label>
//    <br>
//    <br>`)
//	    };
//	  });
//	}
//
//
//// Way #2 of doing getQuizQuestion()
//function getQuizQuestions() {
//	  $.get(`           `, function(res) {
//	    for(i in res) {
//	      $("#questionHolder").append(`<br>
//          <p style="color: #5C6661"><strong> i </strong></p>`)
//          
//          for (j in res.i) {
//          $("#oneQuestion").append(`<br>
//          <input type="radio" name="neighInputType" value="neighCoord" id="neighCoord" required>
//    <label for="neighCoord"> j </label>
//<br>`)
//          };
//	    };
//	  });
//	}
//
//// Example of JSON object for questions and their answers:
//var responseObject = {
//		"Q1) What is blah la?": [ "a", "b", "c" ],
//		"Q2) What is this?": [ "bad", "cow", "idk" ]
//}


const resObj = [
	{
		question: "Who invented Javascript?",
		answers: {
			a: "Douglas Crockford",
			b: "Sheryl Sandberg",
			c: "Brendan Eich"
		},
		correctAnswer: "c"
	},
	
	{
		question: "Which one of these is a js package?",
		answers: {
			a: "Node.js",
			b: "TypeScript",
			c: "npm"
		},
		correctAnswer: "c"
	},
	
	{
		question:"Which tool can you use to ensure code quality?",
		answers: {
			a: "Angular",
			b: "jQuery",
			c: "RequireJS",
			d: "ESLint"
		},
		correctAnswer: "d"
	}
];
	
const questionHolder = document.getElementById("questionHolder");
const resultsHolder = document.getElementById("resultsHolder");
const submitButton = document.getElementById("submitButton");

function buildQuiz() {
	const output = [];
	resObj.forEach(
			(currentQuestion, questionNumber) => {
				const answers = [];
				for (letter in currentQuestion.answers) {
					answers.push(
							`<label>
							<input type="radio" name="question${questionNumber}" value="${letter}">
							${letter} : 
							${currentQuestion.answers[letter]}
							</label>`
							);
				}
				
				output.push(
				`<div class="questions"> ${currentQuestion.question} </div>
				<div class="answers"> ${answers.join("")} </div>`		
				);
			}
			);
	
	questionHolder.innerHTML = output.join("");	
}


function showResults() {
	const answerHolders = questionHolder.querySelectorAll(".answers");
	let numOfCorrect = 0;
	resObj.forEach(
			(currentQuestion, questionNumber) => {	
				const answerHolder = answerHolders[questionNumber];
				const selector = `input[name=question${questionNumber}]:checked`;
				const usersAns = (answerHolder.querySelector(selector) || {}).value;
	
				if (usersAns === currentQuestion.correctAnswer) {
					numOfCorrect++;
					answerHolders[questionNumber].style.color = "lightgreen";
					
				} else {
					answerHolders[questionNumber].style.color = "red";
				}
			});
	resultsHolder.innerHTML = `${numOfCorrect} out of ${myQuestions.length}`;	
}

buildQuiz();

submitButton.addEventListener("click", showResults);

//const resObj = [
//	{
//		question: "Who invented Javascript?",
//		answers: {
//			a: "Douglas Crockford",
//			b: "Sheryl Sandberg",
//			c: "Brendan Eich"
//		},
//		correctAnswer: "c"
//	},
//	
//	{
//		question: "Which one of these is a js package?",
//		answers: {
//			a: "Node.js",
//			b: "TypeScript",
//			c: "npm"
//		},
//		correctAnswer: "c"
//	},
//	
//	{
//		question:"Which tool can you use to ensure code quality?",
//		answers: {
//			a: "Angular",
//			b: "jQuery",
//			c: "RequireJS",
//			d: "ESLint"
//		},
//		correctAnswer: "d"
//	}
//];
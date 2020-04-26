

function getUserAssets(userid, callbackFunction) {
	$.getJSON(
		"AssetsController",
		{ action: 'getAll', userid: userid },
	 	callbackFunction
	);
}

function getUserQuestions(userid, callbackFunction) {
	$.getJSON(
		"QuestionsController",
		{ action: 'getAll', userid: userid },
		callbackFunction
	);
}

function getUserScore(userid, score, callbackFunction) {
	$.getJSON(
		"QuestionsController",
		//those parameters for the answers need to be specified here
		{ action: 'getScore', userid: userid, score:score },
		callbackFunction
	);
}
function getUserAllTimeScore(userid, callbackFunction) {
    $.getJSON(
        "QuestionsController",
        //those parameters for the answers need to be specified here
        { action: 'getUserAllTimeScore', userid: userid},
        callbackFunction
    );
}
function updateAsset(id, userid, description, value, callbackFunction) {
    $.get("AssetsController",
		{ action: "update",
			id: id,
			userid: userid,
			description: description,
			value: value
		},
		callbackFunction
	);
}

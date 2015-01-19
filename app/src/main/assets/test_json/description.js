//==== Every application start if login we have to get
//from the server all user information and
//save it in a persistant storage

//my_user_info.json
{//UserChannel uc = UserChannel.fromJson(data);
	"firstName": "username",
	"lastName": "govnov",
	// "pass": "oia8sd", // token in cookies
	"email": "poc@site.il",
	"avatar": "http://testingo.org/images/215454.png",

	"subscriptions" : [
		{
			"_id": "56a5sd6",
			"name": "Pupsik",
			"avatar": "http://testingo.org/images/215454.png"
		},
		{
			"_id": "56a5sd6",
			"name": "Pocik",
			"avatar": "http://testingo.org/images/215454.png"
		}	
	]
}

//==== Every data activity we should check notifications count

//notifications_count.json
{//NotificationsInfo ni = NotificationsInfo.fromJson(data);
	"notifications" : 3
}

//==== Screen 1 - login
//== Out

{
	"email": "username@ttt.il",
	"pass": "oia8sd"
}

//==In

//login_good

//login_ok.json
{//LoginInfo li = LoginInfo.fromJson(data);
	"token": "xxxxx"
}

//login_bad.json
{//LoginInfo li = LoginInfo.fromJson(data);
	"token": "" 
}

//==== Screen 2 - registration
//==Out

{
	"name": "username",
	"lastName": "govnov",
	"pass": "oia8sd",
	"email": "poc@site.il"


	//image upload.... to be done
}

//==In

//error_no.json
{//ErrorInfo
	"err": "" //name, password, else.. All is good if empty
}

//==== Screen 2 - settings
//==Out

{
	"name": "username",
	"lastName": "govnov",
	"newPass": "oia8sd",
	"oldPass": "oia8sd",
	"email": "poc@site.il"

	//image upload.... to be done
}

//==In

//error_no.json
{//ErrorInfo
	"err": "" //name, password, else.. All is good if empty
}

//==== Screen 3 - Feed

//feed.json
[//TestCard[] tc = TestCard.arrayFromJson(data);
	{//TestCard
		"user": {
			"_id": "56a5sd6",
			"name": "Pupsik",
			"avatar": "http://testingo.org/images/215454.png"
		},
		"test": {
			"_id": "56a5sd6",
			"name": "vasya",
			"image": "http://testingo.org/images/215454.png",
			"description": "super test",
			"count": 5,
			"tags": ["one", "two"]
		}
	},
	{
		"user": {
			"_id": "56a5sd6",
			"name": "Pupsik",
			"avatar": "http://testingo.org/images/215454.png"
		},
		"test": {
			"_id": "56a5sd6",
			"name": "vasya",
			"image": "http://testingo.org/images/215454.png",
			"description": "super test",
			"count": 5,
			"tags": ["one", "two"]
		}
	}
]
//==== Screen 4 - User channel

//channel.json
{ //UserChannel uc = UserChannel.fromJson(data);
	"user": {
			"_id": "56a5sd6",
			"name": "Pupsik",
			"avatar": "http://testingo.org/images/215454.png"
	},
	"channel" : [
		{
			"_id": "56a5sd6",
			"name": "vasya",
			"image": "http://testingo.org/images/215454.png"
			"description": "super test",
			"count": 5,
			"tags" : ["one", "two"],
		},
		{
			"_id": "56a5sd6",
			"name": "vasya",
			"image": "http://testingo.org/images/215454.png"
			"description": "super test",
			"count": 5,
			"tags" : ["one", "two"],
		}
	]
}

//==== Screen 5 - Test detail

//test_details.json
{ //TestDeatailCard tdc = TestDeatailCard.fromJson(data);
	"user": {
		"_id": "56a5sd6",
		"name": "Pupsik",
		"avatar": "http://testingo.org/images/215454.png"
	},
	"test": {
		"_id": "56a5sd6",
		"name": "vasya",
		"date": "2012-01-11T03:34:54Z",
		"showResult": false,
		"image": "http://testingo.org/images/215454.png",
		"description": "super test",
		"count": 5,
		"tags" : ["one", "two"],
		"questionsCount": 4,
		"time": 0, // in seconds
		"hint": "",
	}
}

//===== Screen 6 - 

//question_text.json
//question_check.json
//question_radio.json
//question_image.json

{//Quetion q = Question.fromJson(data);
	"_id": "s5as4d5as",
 	"text": "Кто ты?",
 	"image": "question.png"
	"type": "image", //check, image, radio
	"hint": "",
	"data": ["Олень", "Бобр", "Лось", "Баран"]
}

//==== Screen 7 - Test Result

//resultDetail
{//ResultDetailCard rdc = ResultDetailCard.fromJson(data);
	"user": {
		"_id": "56a5sd6",
		"name": "Pupsik",
		"avatar": "http://testingo.org/images/215454.png"
	},

	"test": {
			"_id": "56a5sd6",
			"name": "vasya",
			"image": "http://testingo.org/images/215454.png",
			"description": "super test",
			"count": 5,
			"tags" : ["one", "two"],
	},	

	"result": {
		"_id": "56a5sd6",
		"img": "",
		"text": "",
		"description" : ""
		"right": true,
		"time": 0
	}
}

//==== Screen 8 - All results

//result_list.json
[ //ResultCard[] rc = ResultCard.arrayFromJson(data);
	{ //ResultCard
		"test": {
				"_id": "56a5sd6",
				"name": "vasya",
				"image": "http://testingo.org/images/215454.png",
				"description": "", //no deascription
				"count": 5,
				"tags" : ["one", "two"]
		},	

		"result": {
			"_id": "56a5sd6",
			"img": "",
			"text": "ты олень",
			"description" : "" //no deascription
			"done": true,
			"time": 0
		}
	}
]

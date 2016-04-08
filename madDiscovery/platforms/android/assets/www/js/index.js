document.addEventListener("deviceready", OnDeviceReady, false);
var db;

function OnDeviceReady() {
	db = window.openDatabase("EventsDB", "1.0", "Events Database", 20000);
	db.transaction(createDB, errDB, successDB);
}

function createDB(ev) {
	// ev.executeSql("DROP TABLE IF EXISTS Events");
	ev.executeSql("CREATE TABLE IF NOT EXISTS Events(id INTEGER PRIMARY KEY  AUTOINCREMENT, name text, location text, date text, time text, organize text,created TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
}

function errDB(ev) {
	navigator.notification.alert(error, null, "Error create DB", "OK");
	console.log('failed');
}

function successDB(ev) {
	navigator.notification.alert("Successfull", null, "Information", "OK");
	console.log('success');
}

function createEvents() {
	var Name = document.getElementById("txtName").value;
	var Locate = document.getElementById("txtLocation").value;
	var Date = document.getElementById("txtDate").value;
	var Time = document.getElementById("txtTime").value;
	var Organize = document.getElementById("txtOrganize").value;
	db.transaction(function (ev) {
		var result = newEvents(ev, Name, Locate, Date, Time, Organize);
	}, errEvents, successEvents);
}

function newEvents(ev, name, location, date, time, organize) {
	var query = "INSERT INTO Events(name,location,date,time,organize) values('" + name + "','" + location + "','" + date + "','" + time + "','" + organize + "')";
	alert(query);
	ev.executeSql(query);
}

function errEvents(error) {
	navigator.notification.alert(error, null, "Create Events Fails", "OK");
	// console.log('failed insert : ' + error.message);
}

function successEvents(ev) {
	navigator.notification.alert("Create Events Success", null, "Information", "OK");
}

//show Events
function ShowEvents() {
	db.transaction(showDB, errDB, successDB);
}

function showDB(ev) {
	ev.executeSql("SELECT id,name, location, date, time,organize FROM Events  ORDER BY created DESC LIMIT 10", [], showSuccess, showErr);
}

//deleteEvents
function deleteEvents(id) {
	db.transaction(function (ev) {
		var result = deleteDB(ev, id);
	}, deleteErr, ShowEvents);
}

function deleteDB(ev, id) {
	var deleteQuery = "DELETE  FROM Events where id=" + id;
	alert(deleteQuery);
	ev.executeSql(deleteQuery);
}

function deleteSuccess() {
	navigator.notification.alert("Delete Events Success", null, "Information", "OK");
}

function deleteErr(err) {
	console.log('failed insert : ' + err.message);
	navigator.notification.alert("Error", null, "Information", "OK");
}

function showSuccess(ev, response) {
	// alert("Response:"+ response);
	var div = document.getElementById("table-result");
	var temp =
		'<thead>' +
		'    <tr>' +
		'        <th style="width:40%">Name</th>' +
		'        <th >ID</th>' +
		'        <th >Location</th>' +
		'        <th >Date</th>' +
		'        <th >Time</th>' +
		'        <th >Organize</th>' +
		'        <th ></th>' +
		'    </tr>' +
		'</thead>';
	temp += "<tbody>";
	for (var i = 0; i < response.rows.length; i++) {
		temp +=
			'<tr>' +
			'    <td class="title">' + response.rows.item(i).name + '</td>' +
			'    <td name="txtID">' + response.rows.item(i).id + '</td>' +
			'    <td>' + response.rows.item(i).location + '</td>' +
			'    <td>' + response.rows.item(i).date + '</td>' +
			'    <td>' + response.rows.item(i).time + '</td>' +
			'    <td>' + response.rows.item(i).organize + '</td>' +
			'<td><button class="ui-btn" onclick="deleteEvents(' + response.rows.item(i).id + ');">Delete</button></td></tr>';
	}
	temp += "</tbody>";
	div.innerHTML = temp;
	$("#table-result").table("rebuild");
}

function showErr() {
	alert('errorr');
}
//search data
/*  function searchEvents() {
      db = window.openDatabase("EventsDB", "1.0", "Events Database", 20000);
      db.transaction(searchName, errDB, successDB);
  }

  function searchName(ev) {
      var search = document.getElementById('txtSearch').value;
      ev.executeSql("SELECT * FROM Events where name like '%" + search + "%'", [], showSuccess, showErr);
  }*/


function validation() {
	if (document.eventform.txtName.value == "") {
		alert("Name of events not invalid.");
		document.eventform.txtName.focus();
	} else if (document.eventform.txtLocation.value == "Choose location") {
		alert("Location not invalid.");
		document.eventform.txtLocation.focus();
	} else if (document.eventform.txtDate.value == "") {
		alert("Date of Events not invalid.");
		document.eventform.txtDate.focus();
	} else if (document.eventform.txtTime.value == "") {
		alert(" Time of Events not invalid.");
		document.eventform.txtTime.focus();
	} else if (document.eventform.txtOrganize.value == "") {
		alert(" Event of Organize not invalid.");
		document.eventform.txtOrganize.focus();
	} else {
		createEvents();
	}
}
$(function () {
	$('#valueEnter').on('keyup', function () {
		var valueIdName = $(this).val();
		db.transaction(function (transaction) {
			transaction.executeSql('SELECT * FROM Events WHERE name LIKE "%' + valueIdName + '%";', [], showSuccess);
		});
	});
});

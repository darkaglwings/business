$(document).ready(function(){
	window.setInterval("setTime()", 1000);
});

function logout(url, struct) {
	ajax("POST", url + "/system/access/logout.jspx", "", false, "window.top.location.href = '" + url + "/system/access/login.jsp';");
}

function setTime() {
	var time = today_picker();
	if (document.getElementById("time")) document.getElementById("time").innerHTML = time;
}
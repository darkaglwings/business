var flag;

$(document).ready($(document).keyup(function(e) {var currKey=0; e=e||event; currKey=e.keyCode||e.which||e.charCode; switch (currKey){case 13: $("#submit").click(); break;}}));

$(document).ready(
	function(e) {
		if ($.browser.msie) {
			var pwd = $('<input type="text" id="pwd" name="pwd" class="ltxt2" value="请输入密码"/>');
			$("#password").after(pwd);
			
			$("#username").val("请输入用户名");
			$("#password").hide();
			
			$("#username").focus(function(e) {if ($("#username").val() == "请输入用户名") $("#username").val("");});
			$("#username").blur(function(e) {if ($("#username").val() == "") $("#username").val("请输入用户名");});
			
			$("#pwd").focus(function(e) {$("#pwd").hide(); $("#password").show(); $("#password").val(""); $("#password").focus();});
			
			$("#password").blur(function(e) {if ($("#password").val() == '') {$("#password").hide(); $("#pwd").show();}});
		}
	}
);

function access(allowNull) {
	flag = allowNull;
	if (validate(allowNull)) {
		save("login", true);
	}
}

function validate() {
	var username = $("#username").val();
	var password = $("#password").val();
	
	if (username == "" || username == "null" || username == "请填写用户名") {
		alert("请填写用户名");
		return false;
	}
	
	if(!flag) {
		if (password == "" || password == "null" || password == "请填写密码") {
			alert("请填写密码");
			return false;
		}
	}
	
	return true;
}
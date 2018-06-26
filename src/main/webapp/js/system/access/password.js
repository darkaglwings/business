var pwd = -1, repwd = -1;

function chgpwd() {
	if (pwd == -1) {
		var id = $("#id").val();
		var password = $("#password").val();
		ajax('POST', 'validate.jspx', 'id=' + id + '&password=' + password, false, 'validate(msg);');
	}
	
	if (pwd == 0);
	if (pwd == 1) {
		alert(document.getElementById("validate").innerHTML);
		return;
	}
	
	if (repwd == -1) {
		revalidate();
	}
	
	if (repwd == 0);
	if (repwd == 1) {
		alert("两次输入新密码不一致");
		return;
	}
	
	if (pwd == 0 && repwd ==0) {
		ajax('POST', 'chgpwd.jspx', 'id=' + $("#id").val() + '&password=' + $("#newpassword").val(), true, 'if (msg == "true") alert("密码修改成功") else alert("密码修改失败");');
	}
}

function revalidate(){
	var result;
	document.getElementById("repassword").style.width = "76%";
	if ($("#newpassword").val() == $("#repassword").val()) {
		document.getElementById("revalidate").innerHTML = "  两次输入新密码一致";
		document.getElementById("revalidate").style.color = "green";
		
		result = true;
		repwd = 0;
	} else {
		document.getElementById("revalidate").innerHTML = "  两次输入新密码不一致";
		document.getElementById("revalidate").style.color = "red";
		
		result = false;
		repwd = 1;
	}
	
	return result;
}

function validate(msg){
	var result;
	document.getElementById("password").style.width = "76%";
	if (msg != "") {
		if (msg == "1") msg = "原始密码不能为空";
		else if (msg == "2") msg = "原始密码不正确";
		else if (msg == "3") msg = "原始密码验证出错";
		else if (msg == "4") msg = "未找到该用户信息";
		
		document.getElementById("validate").innerHTML = "  " + msg;
		document.getElementById("validate").style.color = "red";
		
		result = false;
		pwd = 1;
	} else {
		document.getElementById("validate").innerHTML = "  原始密码校验成功";
		document.getElementById("validate").style.color = "green";
		
		result = true;
		pwd = 0;
	}
	
	return result;
}
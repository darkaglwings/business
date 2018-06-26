var selector, userid;

function resultHandle(nodes) {
	var id = "";
	var name = "";
	
	if (nodes) {
		for (var i = 0; i < nodes.length; i++) {
			if (id == "") {
				id += nodes[i].id;
			} else {
				id += "," + nodes[i].id;
			}

			if (name == "") {
				name += nodes[i].name;
			} else {
				name += "," + nodes[i].name;
			}
		}
	} else {
		id = "";
		name = "";
	}
	
	if (selector == "department") {
		document.getElementById("department").value = id;
		document.getElementById("departmentname").value = name;
		
		ajax("POST", "../userdepartment/assign.jspx", "userid=" + userid + "&department=" + id, true, "");
	} else if (selector == "post") {
		document.getElementById("post").value = id;
		document.getElementById("postname").value = name;
		
		ajax("POST", "../userpost/assign.jspx", "userid=" + userid + "&post=" + id, true, "");
	} else ;
}

function validate() {
	var title = $("#title").val();
	var username = $("#username").val();
	var password = $("#password").val();
	var phone = $("#telephone").val();
	var mobile = $("#cellphone").val();
	var mail = $("#email").val();
	
	if (title == "") {
		$("#title").focus();
		alert("请填写用户名称");
		return false;
	}
	
	if (username != "") {
		var allowNull = $("#allowNull").val();
		if (allowNull != "true") {
			if (password == "") {
				alert("请填写登录密码");
				$("#password").focus();
				return false;
			}
		}
	}
	
	if (!telephone(phone)) {
		alert("联系电话填写不正确");
		return false;
	}
	
	if (!cellphone(mobile)) {
		alert("移动电话填写不正确");
		return false;
	}
	
	if (!email(mail)) {
		alert("电子邮箱填写不正确");
		return false;
	}
	
	return true;
}

function verified(msg){
	document.getElementById("username").style.width = "76%";
	if (msg != "") {
		if (msg == "1") msg = "该用户名已存在";
		else if (msg == "2") msg = "用户名校验出现异常";
		
		document.getElementById("validate").innerHTML = "  " + msg;
		document.getElementById("validate").style.color = "red";
		
		$("a.itembtn10").attr("href", "javascript: alert('" + msg + "');");
	} else {
		document.getElementById("validate").innerHTML = "  该用户名可以使用";
		document.getElementById("validate").style.color = "green";
		
		$("a.itembtn10").attr("href", "javascript: save('detail', false);");
	}
}
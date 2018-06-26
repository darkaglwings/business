var selector;

function iSaveDiv() {
	var id = id_picker("id", false);
	if (id) {
		var role = "";
		
		var roles = document.getElementById("right");
		for (var i = 0; i < roles.length; i++) {
			if (role == "")
				role += roles.options[i].value;
			else
				role += "," + roles.options[i].value;
		}
		
		var url = "../rolemenu/assign.jspx";
		var data = "menuid=" + id + "&role=" + role;
		
		ajax("POST", url, data, true, "iCloseDiv();");
	}
	
	iCloseDiv();
}

function resultHandle(nodes) {
	var menuid = id_picker("id", true);
	if (menuid) {
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
			ajax("POST", "../departmentmenu/assign.jspx", "menuid=" + menuid + "&department=" + id, true, "");
		} else if (selector == "post") {
			ajax("POST", "../postmenu/assign.jspx", "menuid=" + menuid + "&post=" + id, true, "");
		} else if (selector == "user") {
			var adjust = "", index, userid;
			var ids = id.split(",");
			for (var i = 0; i < ids.length; i++) {
				index = ids[i].indexOf("p_");
				if (index != -1) {
					userid = ids[i].substr(index + 2, ids[i].length);
					if (adjust.indexOf(userid) == -1) {
						if (adjust == "") {
							adjust = userid;
						} else {
							adjust += "," + userid;
						}
					}
				}
			}
			
			var url = "../usermenu/assign.jspx";
			var data = "menuid=" + menuid + "&user=" + adjust;
			
			ajax("POST", url, data, true, "");
		}
	}
}
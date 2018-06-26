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
		
		var url = "../userrole/assign.jspx";
		var data = "userid=" + id + "&role=" + role;
		
		ajax("POST", url, data, true, "iCloseDiv();");
	}
	
	iCloseDiv();
}

function resultHandle(nodes) {
	var id = id_picker("id", true);
	if (id) {
		var menu = "";
		if (nodes) {
			for (var i = 0; i < nodes.length; i++) {
				if (menu == "") {
					menu += nodes[i].id;
				}else {
					menu += "," + nodes[i].id;
				}
			}
		}
		
		var url = "../usermenu/assign.jspx";
		var data = "userid=" + id + "&menu=" + menu;

		ajax("POST", url, data, true, "iClose();");
	}
}
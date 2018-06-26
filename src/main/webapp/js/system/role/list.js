var selector;

function resultHandle(nodes) {
	if (selector == "menu") {
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
			
			var url = "../rolemenu/assign.jspx";
			var data = "roleid=" + id + "&menu=" + menu;

			ajax("POST", url, data, true, "iClose();");
		}
	}
	
	if (selector == "user") {
		var id = id_picker("id", true);
		if (id) {
			var index;
			var user = "", userid;
			if (nodes) {
				for (var i = 0; i < nodes.length; i++) {
					index = nodes[i].id.toString().indexOf("p_");
					if (index != -1) {
						userid = nodes[i].id.substr(index + 2, nodes[i].id.length);
						if (user.indexOf(userid) == -1) {
							if (user == "") {
								user = userid;
							}else {
								user += "," + userid;
							}
						}
					}
				}
			}
			
			var url = "../userrole/assign.jspx";
			var data = "roleid=" + id + "&user=" + user;

			ajax("POST", url, data, true, "iClose();");
		}
	}
}
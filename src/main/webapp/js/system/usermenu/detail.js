var selector;

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
		
		if (selector == "menu") {
			document.getElementById("menu").value = id;
			document.getElementById("menuname").value = name;
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
			document.getElementById("user").value = adjust;
			document.getElementById("username").value = name;
		} else ;
	}
}

function validate() {
	var user = $("#user").val();
	var menu = $("#menu").val();
	
	if (user == "" || user == "-1") {
		alert("请选择人员");
		return false;
	}
	
	if (menu == "") {
		alert("请选择菜单");
		return false;
	}
	
	return true;
}
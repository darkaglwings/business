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
		
		document.getElementById("menu").value = id;
		document.getElementById("menuname").value = name;
	}
}

function validate() {
	var role = $("#role").val();
	var menu = $("#menu").val();
	
	if (role == "" || role == "-1") {
		alert("请选择角色");
		return false;
	}
	
	if (menu == "") {
		alert("请选择菜单");
		return false;
	}
	
	return true;
}
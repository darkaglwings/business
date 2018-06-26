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
		
		if (selector == "department") {
			document.getElementById("department").value = id;
			document.getElementById("departmentname").value = name;
		} else if (selector == "menu") {
			document.getElementById("menu").value = id;
			document.getElementById("menuname").value = name;
		} else ;
	}
}

function validate() {
	var department = $("#department").val();
	var menu = $("#menu").val();
	
	if (department == "") {
		alert("请选择部门");
		return false;
	}
	
	if (menu == "") {
		alert("请选择菜单");
		return false;
	}
	
	return true;
}
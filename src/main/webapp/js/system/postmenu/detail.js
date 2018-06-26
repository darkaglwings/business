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
		} else if (selector == "post") {
			document.getElementById("post").value = id;
			document.getElementById("postname").value = name;
		} else ;
	}
}

function validate() {
	var post = $("#post").val();
	var menu = $("#menu").val();
	
	if (post == "") {
		alert("请选择职务");
		return false;
	}
	
	if (menu == "") {
		alert("请选择菜单");
		return false;
	}
	
	return true;
}
function resultHandle(nodes) {
	if (nodes) {
		for (var i = 0; i < nodes.length; i++) {
			document.getElementById("parentid").value = nodes[i].id;
			document.getElementById("parentname").value = nodes[i].name;
			document.getElementById("rank").value = nodes[i].rank + 1;
		}
	} else {
		document.getElementById("parentid").value = "";
		document.getElementById("parentname").value = "";
		document.getElementById("rank").value = "";
	}
}

function validate() {
	var title = $("#title").val();
	
	if (title == "") {
		$("#title").focus();
		alert("请填写菜单名称");
		return false;
	}
	
	return true;
}
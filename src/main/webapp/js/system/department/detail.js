function resultHandle(nodes) {
	if (nodes) {
		for (var i = 0; i < nodes.length; i++) {
			document.getElementById("parentid").value = nodes[i].id;
			document.getElementById("parentname").value = nodes[i].name;
			document.getElementById("rank").value = nodes[i].rank + 1;
			if (!nodes[i].t || nodes[i].t == null || nodes[i].t == "null" || nodes[i].t == "") {
				document.getElementById("fullname").value = nodes[i].name + "/";
			} else {
				document.getElementById("fullname").value = nodes[i].t + "/";
			}
		}
	} else {
		document.getElementById("parentid").value = "";
		document.getElementById("parentname").value = "";
		document.getElementById("rank").value = "";
		document.getElementById("fullname").value = "";
	}
}

function validate() {
	var title = $("#title").val();
	
	if (title == "") {
		$("#title").focus();
		alert("请填写部门名称");
		return false;
	}
	
	return true;
}
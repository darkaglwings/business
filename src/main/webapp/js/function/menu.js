function menued(url, content, multi, msg, type){
	if (!type) {
		type = "menu";
	}
	
	var assigned = JSON.parse(msg);
	var checked = "";
	for (var i = 0; i < assigned.length; i++) {
		if (checked == "") {
			eval("checked = assigned[i]." + type);
		} else {
			eval('checked += "," + assigned[i].' + type);
		}
	}
	
	initTree(url, content, multi, checked);
}

function menu(assigned, treeurl, content, type) {
	var id = id_picker("id", true);
	if (id) {
		var parameter;
		if (type == "menu") {
			parameter = "id=" + id;
		} else {
			parameter = "menuid=" + id;
		}
		
		var callback = 'menued("' + treeurl + '", "' + content + '", true, msg, "' + type + '");';
		ajax("POST", assigned, parameter, true, callback);
	}
}
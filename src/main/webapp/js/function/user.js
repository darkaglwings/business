var id;

function beforeCheck(treeId, treeNode) {
	var id = treeNode.id.toString();
	if (id.indexOf("p_") != -1) {
		return true;
	} else {
		return false;
	}
}

function initDepartment(msg, department, user, content, multi) {
	var users = JSON.parse(msg);
	var checked = "";
	for (var i = 0; i < users.length; i++) {
		if (checked == "") {
			checked = users[i].user;
		} else {
			checked += "," + users[i].user;
		}
	}
	
	ajax("POST", department, "", true, "initUser(msg, '" + user + "', '" + content + "', " + multi + ",'" + checked + "');");
}

function initUser(msg, user, content, multi, checked) {
	var departments = JSON.parse(msg);
	for (var i = 0; i < departments.length; i++) {
		departments[i].isParent = true;
	}
	
	msg = JSON.stringify(departments);
	
	if (msg && msg.length > 1) {
		department = msg.substring(1, msg.length - 1);
	}
	
	ajax("POST", user, "", true, "show(msg, '" + department + "', " + multi + ", '" + checked + "');");
	
	iPopup(content);
}

function show(msg, department, multi, info){
	var data = "";
	if (department && department.length > 1) {
		data = department;
	}
	
	var user;
	var pids;
	var adjust = new Array();
	var users = JSON.parse(msg);
	
	for (var i = 0; i < users.length; i++) {
		users[i].id = "p_" + users[i].id;
		if (users[i].pId) {
			pids = users[i].pId.split(",");
			if (pids.length > 1) {
				for (var j = 0; j < pids.length; j++) {
					user = JSON.parse(JSON.stringify(users[i]));
					user.pId = pids[j];
					user.id = "d" + pids[j] + "_" + user.id;
					adjust.push(user);
				}
			} else {
				adjust.push(users[i]);
			}
		}
	}
	
	msg = JSON.stringify(adjust);
	
	if (msg && msg.length > 1) {
		data = department + "," + msg.substring(1, msg.length - 1);
	}
	
	data = "[" + data + "]";
	
	if (multi == true) {
		setting = {callback: {beforeCheck: beforeCheck}, check: {enable: true}, data : {simpleData : {enable : true}}, view: {fontCss: getFontCss}};
	} else {
		setting = {callback: {beforeCheck: beforeCheck}, check: {enable: true, chkStyle: "radio", radioType: "all"}, data : {simpleData : {enable : true}}, view: {fontCss: getFontCss}};
	}
	
	if (document.getElementById("parameter")) {
		document.getElementById("parameter").value = "";
	}
	
	$.fn.zTree.init($("#tree"), setting, JSON.parse(data));
	if (info) {
		var checked = info.split(",");
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		for (var i = 0; i < checked.length; i++) {
			for (var j = 0; j < nodes.length; j++) {
				if (nodes[j].id == "p_" + checked[i]) {
					treeObj.checkNode(nodes[j], true, false, false);
				}
				
				if (nodes[j].id == "d" + id + "_p_" + checked[i]) {
					treeObj.checkNode(nodes[j], true, false, false);
				}
			}
		}
	}
}

function user(assigned, department, user, content, multi, type) {
	id = id_picker("id", true);
	if (id) {
		ajax("POST", assigned, type + "id=" + id, true, "initDepartment(msg, '" + department + "', '" + user + "', '" + content + "', " + multi + ");");
	}
}
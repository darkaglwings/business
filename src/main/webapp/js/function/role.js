function initRole(msg, assignedRoles) {
	var select_flag = true;
	
	var assigned = JSON.parse(assignedRoles);
	var roles = JSON.parse(msg);
	removeOptions("left");
	removeOptions("right");
	
	for (var i = 0; i < roles.length; i++) {
		select_flag = true;
		for (var j = 0; j < assigned.length; j++) {
			if (roles[i].id == assigned[j].role) {
				select_flag = false;
				document.getElementById("right").add(new Option(roles[i].title, roles[i].id));
				break;
			}
		}
		
		if (select_flag == true) {
			document.getElementById("left").add(new Option(roles[i].title, roles[i].id));
		}
	}
}

function initRoled(msg,url) {
	ajax("POST", url, "query_flag=0", true, "initRole(msg, '" + msg + "');");
}

function role(assigned, role, content, type) {
	document.getElementById("tit_selector").innerHTML = "角色分配";
	var id = id_picker("id", true);
	if (id) {
		ajax("POST", assigned, type + "id=" + id, true, "initRoled(msg, '" + role + "');");
		iPopup(content);
	}
}
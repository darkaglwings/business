function initSubset(msg) {
	document.getElementById("parentid").options.length = 0;
	var parent = JSON.parse(msg);
	document.getElementById("parentid").options.add(new Option("--请选择上级字典--", -1));
	for (var i = 0; i < parent.length; i++) {
		document.getElementById("parentid").options.add(new Option(parent[i].meaning, parent[i].id));
	}
}

function validate() {
	var sort = $("#sort").val();
	var code = $("#code").val();
	var meaning = $("#meaning").val();
	
	if (sort == "") {
		$("#sort").focus();
		alert("请填写字典类型");
		return false;
	}
	
	if (code == "") {
		$("#code").focus();
		alert("请填写字典代码");
		return false;
	}
	
	if (meaning == "") {
		$("#meaning").focus();
		alert("请填写字典内容");
		return false;
	}
	
	return true;
}
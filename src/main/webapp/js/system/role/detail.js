function validate() {
	var title = $("#title").val();
	var rank = $("#rank").val();
	
	if (title == "") {
		$("#title").focus();
		alert("请填写角色名称");
		return false;
	}
	
	if (!number(rank)) {
		$("#rank").focus();
		alert("角色等级只能为数字");
		return false;
	}
	
	return true;
}
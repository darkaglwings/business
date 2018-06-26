function validate() {
	var title = $("#title").val();
	var code = $("#code").val();
	
	if (title == "") {
		$("#title").focus();
		alert("请填写参数名称");
		return false;
	}
	
	if (code == "") {
		$("#code").focus();
		alert("请填写参数内容");
		return false;
	}
	
	return true;
}
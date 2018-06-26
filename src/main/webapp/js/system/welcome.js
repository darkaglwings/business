$(document).ready(function(){
	window.setInterval("resizeIframe()", 200);
});

function resizeIframe(){
	try{
		iframe = document.getElementById("left");
		var bHeight = iframe.contentWindow.document.body.scrollHeight;
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		if (bHeight && dHeight) {
			iframe.height = Math.max(bHeight, dHeight).toString();
		} else if (bHeight && !dHeight) {
			iframe.height = bHeight.toString();
		} else if (!bHeight && dHeight) {
			iframe.height = dHeight.toString();
		} else if (!bHeight && !dHeight) {
			iframe.height = "100%";
		} else ;
		
		if (document.getElementById("menu_switch").className == 'current') {
			document.getElementById("left").style.width = "240px";
		} else if (that.className == '') {
			document.getElementById("left").style.width = "0px";
		} else ;
	}catch (ex){ }
	
	try{
		var iframe = document.getElementById("right");
		var bHeight = iframe.contentWindow.document.body.scrollHeight;
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		if (bHeight && dHeight) {
			iframe.height = Math.max(bHeight, dHeight).toString();
		} else if (bHeight && !dHeight) {
			iframe.height = bHeight.toString();
		} else if (!bHeight && dHeight) {
			iframe.height = dHeight.toString();
		} else if (!bHeight && !dHeight) {
			iframe.height = "100%";
		} else ;
	}catch (ex){ }
}
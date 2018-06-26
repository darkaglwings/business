function initMenu(key) {
	if (sessionStorage.getItem(key)) {
		on_off(sessionStorage.getItem(key));
	}
}

function load(url){
	window.parent.document.getElementById("right").src = url;
}

function menuSwitch(that) {
	if (that.className == 'current') {
		that.className = '';
		document.getElementById("left").style.width = "0px";
	} else if (that.className == '') {
		that.className = 'current';
		document.getElementById("left").style.width = "240px";
	} else ;
}

function storage(key, val) {
	if (sessionStorage.getItem(key) == val) 
		sessionStorage.removeItem(key); 
	else 
		sessionStorage.setItem(key, val);
}
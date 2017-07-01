function GoRegister(e) {
	var errField = document.getElementById("error");
	var xReq = new XMLHttpRequest();
	var form = e.target.parentElement;
	while (form != null && form.tagName != "FORM") {
		form = form.parentElement;
	}
	if (form.reportValidity === undefined || !form.reportValidity()) {
		return;
	}
	var body = new FormData(e.target.parentElement);
	xReq.onload = function(e) {
			var response = JSON.parse(xReq.response);
			if (response.code != 0) {
				errField.innerText = response.message;
			} else {
				window.location.href = "/confirm.html"
			}
		}
	xReq.open('POST', '/api/register', true)
	xReq.send(body);
	e.preventDefault()
}

window.addEventListener('load', function(e) {
			var el = document.getElementById("registerSubmit");
			if (el != null) {
				el.addEventListener('click', GoRegister);
			}
		}
	);

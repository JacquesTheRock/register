function GetRegistered(target) {
	if(target == null) {
		return;
	}
	var xReq = new XMLHttpRequest();
	var body = null
	xReq.onload = function(e) {
			var entries = JSON.parse(xReq.response);
			while(target.rows.length > 0) {
				target.deleteRow(0)
			}
			for(var i = 0; i < entries.length; i++) {
				var e = entries[i];
				var row = target.insertRow(target.rows.length);
				row.insertCell(0).innerText = e.date;
				row.insertCell(0).innerText = e.country;
				row.insertCell(0).innerText = e.zip;
				row.insertCell(0).innerText = e.state;
				row.insertCell(0).innerText = e.city;
				row.insertCell(0).innerText = e.addr2;
				row.insertCell(0).innerText = e.addr1;
				row.insertCell(0).innerText = e.last;
				row.insertCell(0).innerText = e.first;
			}
		}
	xReq.open('GET', '/api/register', true)
	xReq.send(body);
}

window.addEventListener('load', function(e) {
			GetRegistered(document.getElementById("registrees"))
		}
	);

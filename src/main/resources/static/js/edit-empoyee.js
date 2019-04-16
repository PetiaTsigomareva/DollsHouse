$(document).ready(function () {
    $('#inputOffice').on('change', function () {
    	let selectedOffice = document.getElementById("inputOffice").value;
    	
    	console.log(selectedOffice);
    	
    	let serviceSelect = $('#divinputService');
    	if (selectedOffice === 'Please select...') {
    	  serviceSelect.hide();
      } else {
        loadOfficeServices(selectedOffice);
        serviceSelect.show();
      }
    });
});

function loadOfficeServices(officeId) { 
  fetch('http://localhost:8080/service/fetch/' + officeId).then((response) => response.json()).then((json) => {
    var serviceSelect = $('#inputService');
    
    json.forEach((x) => {
      var o = document.createElement("option");
      o.name=x.id;
      o.value=x.id;
      o.text=x.name;
      
      serviceSelect[0].options.add(o);
    });
});
}

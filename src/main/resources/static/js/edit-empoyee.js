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
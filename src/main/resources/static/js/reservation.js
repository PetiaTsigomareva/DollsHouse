$(document).ready(
    function() {
	    var calendarFirstDate = new Date();
	    calendarFirstDate.setHours(0, 0, 0, 0);
	    var calendarLastDate = new Date();
	    calendarLastDate.setHours(0, 0, 0, 0);
	    calendarLastDate.setDate(calendarLastDate.getDate() + 2);

	    checkAndSetPreviousButton();

	    $('#inputOffice').on('change', function() {
		    $('#divinputService').hide();
		    $('#divinputEmployee').hide();
		    $('#available-hours').hide();

		    let selectedOffice = document.getElementById("inputOffice").value;

		    let serviceSelect = $('#divinputService');
		    if (selectedOffice === 'Please select...') {
			    serviceSelect.hide();
		    } else {
			    loadOfficeServices(selectedOffice);
			    serviceSelect.show();
		    }
	    });

	    $('#inputService').on('change', function() {
		    $('#divinputEmployee').hide();
		    $('#available-hours').hide();

		    let selectedService = document.getElementById("inputService").value;

		    let employeeSelect = $('#divinputEmployee');
		    if (selectedService === 'Please select...') {
			    employeeSelect.hide();
		    } else {
			    loadServiceEmployees(selectedService);
			    employeeSelect.show();
		    }
	    });

	    $('#inputEmployee').on(
	        'change',
	        function() {
		        $('#available-hours').hide();

		        let selectedEmployee = document.getElementById("inputEmployee").value;

		        let availableHours = $('#available-hours');
		        let description = $('#divinputDescription');
		        if (selectedEmployee === 'Please select...') {
			        description.hide();
			        availableHours.hide();
		        } else {
			        fillAvailableHours(document.getElementById("inputService").value, document.getElementById("inputEmployee").value, formatDate(calendarFirstDate),
			            formatDate(calendarLastDate));
			        description.show();
			        availableHours.show();
		        }
	        });

	    $('#previousDays').on(
	        'click',
	        function() {
		        calendarFirstDate.setDate(calendarFirstDate.getDate() - 1);
		        calendarLastDate.setDate(calendarLastDate.getDate() - 1);

		        checkAndSetPreviousButton();

		        fillAvailableHours(document.getElementById("inputService").value, document.getElementById("inputEmployee").value, formatDate(calendarFirstDate),
		            formatDate(calendarLastDate));
	        });

	    $('#nextDays').on(
	        'click',
	        function() {
		        calendarFirstDate.setDate(calendarFirstDate.getDate() + 1);
		        calendarLastDate.setDate(calendarLastDate.getDate() + 1);

		        checkAndSetPreviousButton();

		        fillAvailableHours(document.getElementById("inputService").value, document.getElementById("inputEmployee").value, formatDate(calendarFirstDate),
		            formatDate(calendarLastDate));
	        });

	    function checkAndSetPreviousButton() {
		    let tomorrow = new Date();
		    tomorrow.setHours(0, 0, 0, 0);
		    tomorrow.setDate(tomorrow.getDate() + 1);

		    if (calendarFirstDate < tomorrow) {
			    document.getElementById("previousDays").setAttribute("disabled", true);
		    } else {
			    document.getElementById("previousDays").removeAttribute("disabled");
		    }
	    }
    });
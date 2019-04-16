$(document).ready(function () {
    $('#inputOffice').on('change', function () {
    	let selectedOffice = document.getElementById("inputOffice").value;
    	
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
     $('#divinputService').empty();
      let servicesSelect = `<select class="form-control" id="inputService" name="serviceId">`
        + `<option value="Please select...">Please select...</option>`
        
        json.forEach((x) => {
          servicesSelect += `<option name="" value="${x.id}">${x.name}</option>`;
        });
        
        servicesSelect += `</select>`;
        
        $('#divinputService').append(servicesSelect);
        
        $('#inputService').on('change', function () {
          let selectedService = document.getElementById("inputService").value;

          let employeeSelect = $('#divinputEmployee');
          if (selectedService === 'Please select...') {
            employeeSelect.hide();
          } else {
            loadServiceEmployees(selectedService);
            employeeSelect.show();
          }
        });
});
}

function loadServiceEmployees(serviceId) { 
  fetch('http://localhost:8080/users/fetch/' + serviceId).then((response) => response.json()).then((json) => {
     $('#divinputEmployee').empty();
     
     console.log(json);
     
      let employeesSelect = `<select class="form-control" id="inputEmployee" name="employeeId">`
        + `<option value="Please select...">Please select...</option>`
        
        json.forEach((x) => {
          employeesSelect += `<option name="" value="${x.id}">${x.name}</option>`;
        });
        
      employeesSelect += `</select>`;
        
        $('#divinputEmployee').append(employeesSelect);
        
        $('#inputEmployee').on('change', function () {
          let selectedEmployee = document.getElementById("inputEmployee").value;

          let availableHours = $('#available-hours');
          if (selectedEmployee === 'Please select...') {
            availableHours.hide();
          } else {
            availableHours.show();
          }
        });
});
}
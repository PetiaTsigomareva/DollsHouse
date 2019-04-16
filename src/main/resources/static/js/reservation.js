$(document).ready(function () {
  $('#inputOffice').on('change', function () {
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

  $('#inputService').on('change', function () {
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

  $('#inputEmployee').on('change', function () {
    $('#available-hours').hide();

    let selectedEmployee = document.getElementById("inputEmployee").value;
    
    let availableHours = $('#available-hours');
    if (selectedEmployee === 'Please select...') {
      availableHours.hide();
    } else {
      availableHours.show();
    }
  });
});

function loadOfficeServices(officeId) {
  fillSelectOptions('http://localhost:8080/service/fetch/' + officeId, 'inputService');
}

function loadServiceEmployees(serviceId) {
  $('#available-hours').hide;
  fillSelectOptions('http://localhost:8080/users/fetch/' + serviceId, 'inputEmployee');
}

function fillSelectOptions(fetchURL, selectId){
  var selectToFill = $('#'+selectId);
  
  fetch(fetchURL).then((response) => response.json()).then((json) => {
    clearSelectOptions(selectToFill);

    console.log(json);

    json.forEach((x) => {
      var o = document.createElement("option");
      o.name=x.id;
      o.value=x.id;
      o.text=x.name;
      
      selectToFill[0].options.add(o);
    });
  });
}

function clearSelectOptions(select){
  // clear all options
  var length = select[0].options.length;
  for (i = 1; i < length; i++) {
    select[0].options[i] = null;
  }
}
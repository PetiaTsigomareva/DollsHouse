$(document).ready(function() {
  let selectedOffice = document.getElementById("inputOffice").value;
  let serviceSelect = $('#divinputService');

  if (selectedOffice === 'Please select...') {
    serviceSelect.hide();
  } else {
    loadOfficeServices(selectedOffice);
    serviceSelect.show();
  }

  $('#inputOffice').on('change', function() {
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
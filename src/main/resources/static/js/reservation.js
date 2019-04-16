$(document).ready(function () {
  var calendarFirstDate = new Date();
  var calendarLastDate = new Date();
  calendarLastDate.setDate(calendarLastDate.getDate() + 2);

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
      fillAvailableHours( document.getElementById("inputService").value, document.getElementById("inputEmployee").value, formatDate(calendarFirstDate), formatDate(calendarLastDate));
      availableHours.show();
    }
  });

  $('#previousDays').on('click', function () {
    calendarFirstDate.setDate(calendarFirstDate.getDate() - 1);
    calendarLastDate.setDate(calendarLastDate.getDate() - 1);

    fillAvailableHours( document.getElementById("inputService").value, document.getElementById("inputEmployee").value, formatDate(calendarFirstDate), formatDate(calendarLastDate));
  });

  $('#nextDays').on('click', function () {
    calendarFirstDate.setDate(calendarFirstDate.getDate() + 1);
    calendarLastDate.setDate(calendarLastDate.getDate() + 1);

    fillAvailableHours( document.getElementById("inputService").value, document.getElementById("inputEmployee").value, formatDate(calendarFirstDate), formatDate(calendarLastDate));
  });

});

function formatDate(date){
  var yyyy = date.getFullYear();
  var mm = String(date.getMonth() + 1).padStart(2, '0'); // January is 0!
  var dd = String(date.getDate()).padStart(2, '0');

  date = yyyy + '-' + mm + '-' + dd;

  return date;
}

function formatDayOfWeek(date){
  var dayOfWeekId = new Date(date).getDay();

  var weekday = new Array(7);
  weekday[0] = "Sunday";
  weekday[1] = "Monday";
  weekday[2] = "Tuesday";
  weekday[3] = "Wednesday";
  weekday[4] = "Thursday";
  weekday[5] = "Friday";
  weekday[6] = "Saturday";

  return weekday[dayOfWeekId];
}

function loadOfficeServices(officeId) {
  fillSelectOptions('http://localhost:8080/service/fetch/' + officeId, 'inputService');
}

function loadServiceEmployees(serviceId) {
  $('#available-hours').hide;
  fillSelectOptions('http://localhost:8080/users/fetch/' + serviceId, 'inputEmployee');
}

function fillAvailableHours(serviceId, emloyeeId, fromDate, toDate){
  fetch('http://localhost:8080/service/availabilities?serviceId=' + serviceId + '&emloyeeId=' + emloyeeId + '&fromDate=' + fromDate + '&toDate=' + toDate).then((response) => response.json()).then((json) => {
    var i = 0;
    json.forEach((x) => {
      i++;

      let date = x.date;
      let dayOfWeek = formatDayOfWeek(x.date);
      
      let calendarDate = document.getElementById("calendarDate" + i);
      let calendarDayOfWeek = document.getElementById("calendarDayOfWeek" + i);
            
      calendarDate.innerHTML = date;
      calendarDayOfWeek.innerHTML = dayOfWeek;
      
      var hours = x.hours;
      for(var j = 0; j < 10; j++){
        var calendarHour = document.getElementById("calendarHour" + i + j);
        let hourTime = hours[j].hour;
        let hourAvailability = hours[j].availability;
        
        if (hourAvailability === 'AVAILABLE'){
          calendarHour.innerHTML = '<a href="#">' + hourTime + '</a>';
          calendarHour.style.backgroundColor = "green";
        } else if (hourAvailability === 'PENDING_CONFIRMATION'){
          calendarHour.innerHTML = hourTime;
          calendarHour.style.backgroundColor = "yellow";
        } else {
          calendarHour.innerHTML = hourTime;
          calendarHour.style.backgroundColor = "red";
        }
      }
    });
  });
}

function fillSelectOptions(fetchURL, selectId){
  var selectToFill = $('#'+selectId);
  
  fetch(fetchURL).then((response) => response.json()).then((json) => {
    clearSelectOptions(selectToFill);

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
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
  fillSelectOptions('http://localhost:8080/service/fetch/' + officeId, 'Service');
}

function loadServiceEmployees(serviceId) {
  $('#available-hours').hide;
  fillSelectOptions('http://localhost:8080/users/fetch/' + serviceId, 'Employee');
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
          let buttonId = date + 'T' + hourTime;
          calendarHour.innerHTML = '<button type="button" id="' + buttonId + '">' + hourTime + '</button>';

          document.getElementById(buttonId).addEventListener("click", function(){
        	  let button = document.getElementById(buttonId);

        	  var allButtons = document.querySelectorAll('button[type="button');
        	  allButtons.forEach(function(currentButton) {
        	    currentButton.style.backgroundColor = "transparent";
        	  })
        	  
      	      button.style.backgroundColor = "red";
        	  document.getElementById("selectedHour").setAttribute("value", button.getAttribute("id"));
        	  document.getElementById("buttonmakeReservation").removeAttribute("disabled");
        	});

        } else if (hourAvailability === 'PENDING_CONFIRMATION'){
          calendarHour.innerHTML = hourTime;
        } else {
          calendarHour.innerHTML = hourTime;
        }
      }
    });
  });
}

function fillSelectOptions(fetchURL, selectId){
  var selectToFill = $('#input'+selectId);
  
  selectToFill.empty();
  
  var o = document.createElement("option");
  o.name='Please select ' + selectId + ' ...';
  o.value='Please select ' + selectId + ' ...';
  o.text='Please select ' + selectId + ' ...';
  selectToFill[0].options.add(o);

  fetch(fetchURL).then((response) => response.json()).then((json) => {
    json.forEach((x) => {
      o = document.createElement("option");
      o.name=x.id;
      o.value=x.id;
      o.text=x.name;
      
      selectToFill[0].options.add(o);
    });
  });
}
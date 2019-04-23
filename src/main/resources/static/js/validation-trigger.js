$(document).ready(function() {
  var all = document.getElementsByTagName("*");

  for (var i = 0, max = all.length; i < max; i++) {
    var event = new Event('keyup');
    all[i].dispatchEvent(event);
    event.stopPropagation();
  }
});
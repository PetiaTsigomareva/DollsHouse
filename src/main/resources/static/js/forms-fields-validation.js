function valudateInput(fieldId, regexText, helpMessage) {
  let field = $('#' + fieldId);
  let valueToCheck = field.val().trim();
  let helpTextPlaceholderElement = $('#' + fieldId + 'Help');
  var regex = new RegExp(regexText);

  console.log(fieldId);
  
  if (regex.test(valueToCheck)) {
    removeHelpText(field, helpTextPlaceholderElement);
  } else {
    showHelpText(field, helpTextPlaceholderElement, helpMessage);
  }
}

function removeHelpText(field, helpText) {
  field.removeClass("border border-danger");
  field.addClass("border border-success");
  helpText.hide();
}

function showHelpText(field, helpText, message) {
  field.removeClass("border border-success");
  field.addClass("border border-danger");
  helpText.text(message);
  helpText.show();
}
function validFormFields() {

    const  INVALID_IDENTIFICATION_CODE_MESSAGE ="Field must be 9 number!";
    const  EMPTY_MESSAGE = "Field can not be empty!";
    const  INVALID_USERNAME_MESSAGE = "Field must be between 5 and 10 symbols";
    const  INVALID_PASSWORD_MESSAGE ="Field must be min 6 symbols - least one digit, upper letter, lower letter and special symbols!";
    const  INVALID_CONFIRM_PASSWORD_MESSAGE ="Confirm field not math with password field!";
    const  INVALID_EMAIL_MESSAGE="Field must be valid email!";
    const  INVALID_PHONE_NUMBER_MESSAGE = "Field must be between 7 and 12 symbols";
    const  INVALID_PRICE_MESSAGE="Field must be bigger than 0.01...";
    const  INVALID_DATE_MESSAGE="Field must be in format \"yyyy-MM-dd\"";
    const  INVALID_DATE_TIME_MESSAGE= "Field must be in format \"yyyy-MM-dd'T'HH:mm\"";
    const  INVALID_STATUS_MESSAGE="Field must be PendingConfirmation, Confirmed or Rejected;";


    function handleNull( fieldId, helperId ) {
        let field = $(fieldId);
        let helpText = $(helperId);
        let value = field.val().trim();

        if (value == null || value === "") {
            showHelpText(field, helpText, EMPTY_MESSAGE);
        } else {
            removeHelpText(field, helpText);
        }
    }

    function handleInterval( fieldId, helperId, from, to ) {
        let field = $(fieldId);
        let helpText = $(helperId);
        let value = field.val().trim();

        if (value == null || value === "" || value < from || value > to) {
            showHelpText(field, helpText, EMPTY_MESSAGE);
        } else {
            removeHelpText(field, helpText);
        }
    }

    function companyFieldsHelperHandle( fieldId, helperId ) {
        let field = $(fieldId);
        let helpText = $(helperId);
        let value = field.val().trim();

        if (value.length < 9 || value == null || value === "") {
            showHelpText(field, helpText, INVALID_IDENTIFICATION_CODE_MESSAGE);
        } else {
            removeHelpText(field, helpText);
        }
    }

  //  $('#inputBoatName').on(`keyup keypress`, helperHandle('#inputBoatName', '#nameHelp') );


    $(`#inputBoatPrice`).on(`keyup keypress`, function () {

        let field = $(`#inputBoatPrice`);
        let helpText = $(`#priceHelp`);

        let value = field.val().trim();
        if (parseFloat(value) < 0.01 || value == null || value === "") {
            showHelpText(field, helpText, ERROR_INVALID_BOAT_PRICE);
        } else {
            removeHelpText(field, helpText);
        }
    });


    $(`#inputStartDate`).on(`keyup keypress`, function () {

        let field = $(`#inputStartDate`);
        let helpText = $(`#dateHelp`);

        let value = field.val().trim();

        if (value.length < 1 || value.length > 50 || value == null || value === "") {
            showHelpText(field, helpText, INVALID_DATE_MESSAGE);
        } else {
            removeHelpText(field, helpText);
        }
    });



    $(`#inputPhone`).on(`keyup keypress`, function () {

        let field = $(`#inputPhone`);
        let helpText = $(`#inputPhoneHelp`);

        let value = field.val().trim();

        if (value<7 || value >12 || value == null || value === "") {
            showHelpText(field, helpText, INVALID_PHONE_NUMBER_MESSAGE);
        } else {
            removeHelpText(field, helpText);
        }
    });


    $(`#inputEmail`).on(`keyup keypress`, function () {

        let field = $(`#inputEmail`);
        let helpText = $(`#inputEmailHelp`);

        let value = field.val().trim();

        if (value<6 || value == null || value === "") {
            showHelpText(field, helpText, INVALID_EMAIL_MESSAGE);
        } else {
            removeHelpText(field, helpText);
        }
    });



    function removeHelpText(field, helpText) {
        field.removeClass("border border-danger");
        field.addClass("border border-success");
        helpText.hide();
    }

    function showHelpText(field, helpText, message) {
        field.addClass("border border-danger");
        helpText.text(message);
        helpText.show();
    }


}
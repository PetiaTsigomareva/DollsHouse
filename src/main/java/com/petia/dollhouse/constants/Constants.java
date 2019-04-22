package com.petia.dollhouse.constants;

import java.time.format.DateTimeFormatter;

public interface Constants {
	public static final String USERNAME_REGEX = "^([0-9a-zA-Z_]{5,10})$";
	public static final String PASSWORD_REGEX = "^[a-zA-Z-0-9!@#$%^&*()_+]{6,}$";
	public static final String EMAIL_REGEX = "^([0-9a-zA-Z_.]+@[0-9a-zA-Z_.]+\\.[a-z]+)$";
	public static final String PHONE_NUMBER_REGEX = "^([0-9]{7,12})$";
	public static final String MIN_TEXT_FIELD_REGEX = "^(.{3,})$";
	public static final String MIN_TEXT_FOUR_FIELD_REGEX = "^(.{4,})$";
	public static final String MIN_PRICE_REGEX = "^(([0]\\.[0-9][1-9])|([1-9][0-9]*(\\.[0-9]{2})?))$";
	public static final String ADDRESS_REGEX = "^(.{15,})$";
	public static final String ID_NUMBER_REGEX = "^([0-9]{9})$";
	public static final String EMPTY_FIELD_REGEX = "^(.{1,})$";

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

	public static final String TIME_HOUR_FORMAT = ":00";
	public static final String TIME_ZERO_FORMAT = "0";
	public static final Integer YEAR = 2019;
	public static final Integer MONTH = 12;
	public static final Integer DAYS = 28;
	public static final Integer FIRST = 1;

	public static final String MODEL = "model";
	public static final String BINDING_MODEL = "bindingModel";
	public static final String CUSTOMER = "Customer";
	public static final String EMPLOYEE = "Employee";
	public static final String USER = "user";
	public static final String MODERATOR = "moderator";
	public static final String ADMIN = "admin";
	public static final String COMPANY_NAME = "Dolls House";
	public static final String FORM_METHOD = "post";
	public static final String RESERVATION_REJECT = "Rejected";
	public static final String RESERVATION_CONFIRM = "Confirmed";
	public static final String RESERVATION_PENDING = "Pending-Confirmation";

	// urls
	public static final String INDEX_ACTION = "/";
	public static final String HOME_ACTION = "/home";
	public static final String ABOUT_ACTION = "/about";
	public static final String CONTACT_ACTION = "/contact";
	public static final String ADMIN_SIDEBAR_ACTION = "/admin";
	public static final String MODERATOR_SIDEBAR_ACTION = "/moderator";

	public static final String USER_CONTEXT = "/users";
	public static final String PROFILE_ACTION = USER_CONTEXT + "/profile";
	public static final String EDIT_PROFILE_ACTION = USER_CONTEXT + "/edit-profile/";
	public static final String REGISTER_FORM_ACTION = USER_CONTEXT + "/register";
	public static final String LOGIN_FORM_ACTION = USER_CONTEXT + "/login";
	public static final String ADD_EMPLOYEE_ACTION = USER_CONTEXT + "/add-employee";
	public static final String EDIT_EMPLOYEE_ACTION = USER_CONTEXT + "/edit-employee/";
	public static final String DELETE_EMPLOYEE_ACTION = USER_CONTEXT + "/delete-employee/";
	public static final String AlL_EMLOYEES_ACTION = USER_CONTEXT + "/all-employees";
	public static final String SET_USER_ACTION = USER_CONTEXT + "/set-user/";
	public static final String SET_MODERATOR_ACTION = USER_CONTEXT + "/set-moderator/";
	public static final String SET_ADMIN_ACTION = USER_CONTEXT + "/set-admin/";
	public static final String FETCH_SERVICE_ALL_EMPLOYEES_ACTION = USER_CONTEXT + "/fetch/";
	public static final String ADD_EMPLOYEE_PAGE = USER_CONTEXT + "/add-employee";
	public static final String EDIT_EMPLOYEE_PAGE = USER_CONTEXT + "/edit-employee";
	public static final String DELETE_EMPLOYEE_PAGE = USER_CONTEXT + "/delete-employee";
	public static final String ALL_EMPLOYEES_PAGE = USER_CONTEXT + "/all-employees";

	public static final String ADD_COMPANY_ACTION = "/company/add-company";
	public static final String EDIT_COMPANY_ACTION = "/company/edit-company/";
	public static final String DELETE_COMPANY_ACTION = "/company/delete-company/";
	public static final String ALL_COMPANY_ACTION = "/company/all-companies";

	public static final String ADD_OFFICE_ACTION = "/office/add-office";
	public static final String EDIT_OFFICE_ACTION = "/office/edit-office/";
	public static final String DELETE_OFFICE_ACTION = "/office/delete-office/";
	public static final String ALL_OFFICE_ACTION = "/office/all-offices";

	public static final String SERVICE_CONTEXT = "/service";
	public static final String ADD_SERVICE_ACTION = SERVICE_CONTEXT + "/add-service";

	public static final String EDIT_SERVICE_ACTION = SERVICE_CONTEXT + "/edit-service/";
	public static final String DELETE_SERVICE_ACTION = SERVICE_CONTEXT + "/delete-service/";
	public static final String ALL_SERVICE_ACTION = SERVICE_CONTEXT + "/all-service";
	public static final String FETCH_OFFICE_ALL_SERVICES_ACTION = SERVICE_CONTEXT + "/fetch/";
	public static final String FETCH_AVAILABILITIES = SERVICE_CONTEXT + "/availabilities";

	public static final String ADD_RESERVATION_ACTION = "/reservation/add-reservation";
	public static final String EDIT_RESERVATION_ACTION = "/reservation/edit-reservation/";
	public static final String DELETE_RESERVATION_ACTION = "/reservation/delete-reservation/";
	public static final String MY_RESERVATIONS_ACTION = "/reservation/my-reservations-form";

	public static final String ADD_MODERATOR_RESERVATION_ACTION = "/reservation/add-reservation-form";
	public static final String EDIT_MODERATOR_RESERVATION_ACTION = "/reservation/edit-reservation-form/";
	public static final String REJECT_MODERATOR_RESERVATION_ACTION = "/reservation/reject-reservation-form/";
	public static final String ALL_RESERVATIONS_ACTION = "/reservation/all-reservation-form";
	public static final String CONFIRM_MODERATOR_RESERVATION_ACTION = "/reservation/confirm-reservation/";

	public static final String PROMO_OFFER_ACTION = "/promo-offers";

	public static final String LOGOUT_FORM_ACTION = "/logout?login";

	// pages name
	public static final String REGISTER_PAGE = "/register";
	public static final String LOGIN_PAGE = "/login";
	public static final String INDEX_PAGE = "index";
	public static final String HOME_PAGE = "home";
	public static final String ABOUT_PAGE = "about";
	public static final String CONTACT_PAGE = "contact";
	public static final String ADMIN_SIDEBAR_PAGE = "admin";
	public static final String MODERATOR_SIDEBAR_PAGE = "moderator";

	public static final String PROFILE_PAGE = "/profile";
	public static final String EDIT_PROFILE_PAGE = "/edit-profile";
	public static final String ADD_COMPANY_PAGE = "/company/add-company";
	public static final String ALL_COMPANY_PAGE = "/company/all-companies";
	public static final String EDIT_COMPANY_PAGE = "/company/edit-company";
	public static final String DELETE_COMPANY_PAGE = "/company/delete-company";
	public static final String ADD_OFFICE_PAGE = "/office/add-office";
	public static final String EDIT_OFFICE_PAGE = "/office/edit-office";
	public static final String DELETE_OFFICE_PAGE = "/office/delete-office";
	public static final String ALL_OFFICE_PAGE = "/office/all-offices";

	public static final String ADD_SERVICE_PAGE = "/service/add-service";
	public static final String EDIT_SERVICE_PAGE = "/service/edit-service";
	public static final String DELETE_SERVICE_PAGE = "/service/delete-service";
	public static final String ALL_SERVICE_PAGE = "/service/all-service";

	public static final String ADD_RESERVATION_PAGE = "/reservation/add-reservation";
	public static final String EDIT_RESERVATION_PAGE = "/reservation/edit-reservation";
	public static final String DELETE_RESERVATION_PAGE = "/reservation/delete-reservation";

	public static final String ADD_MODERATOR_RESERVATION_PAGE = "/reservation/add-reservation-form";
	public static final String EDIT_MODERATOR_RESERVATION_PAGE = "/reservation/edit-reservation-form";
	public static final String REJECT_MODERATOR_RESERVATION_PAGE = "/reservation/reject-reservation-form";
	public static final String CONFIRM_MODERATOR_RESERVATION_PAGE = "/reservation/confirm-reservation-form";
	public static final String ALL_RESERVATIONS_PAGE = "/reservation/all-reservation-form";
	public static final String MY_RESERVATIONS_PAGE = "/reservation/my-reservations-form";

	public static final String ERROR_GENERAL_PAGE = "/errors/general-error";

	// Error Massages
	// false = redirect, true = throw exception
	public static final boolean THROW_EXCEPTION_FOR_INVALID_DATA_IN_CONTROLLER = true;
	public static final String INVALID_DATA_IN_CONTROLLER_MESSAGE = "Invalid data - item can not added!";
	public static final String INVALID_STATUS_MESSAGE = "Field must be PendingConfirmation, Confirmed or Rejected;";
	public static final String USERNAME_ERROR_MESSAGE = "Username not found!";
	public static final String EXIST_USERNAME_ERROR_MESSAGE = "Already exists user with this username!";
	public static final String EXIST_ITEM_ERROR_MESSAGE = "Item Already exists!";
	public static final String PASSWORD_ERROR_MESSAGE = "Incorrect password!";
	public static final String ID_ERROR_MESSAGE = "Invalid id!";
	public static final String ERROR_MESSAGE = "The searched item not found!";
	public static final String CRITERIA_ERROR_MESSAGE = "Enter criteria is invalid!";
	public static final String RESERVATION_STATUS_ERROR_MESSAGE = "Invalid Reservation Status";
	public static final String ERROR_MESSAGE_TITLE = "message";
	public static final String INVALID_ROLE = "Invalid role name";

	// Pages Titles
	public static final String ADD = "Add";
	public static final String EDIT = "Edit";
	public static final String DELETE = "Delete";
	public static final String ALL = "All";

	public static final String ADD_EMPLOYEE_TITLE = ADD + " Employees";
	public static final String EDIT_EMPLOYEE_TITLE = EDIT + " Employee";
	public static final String DELETE_EMPLOYEE_TITLE = DELETE + " Employee";
	public static final String ALL_EMPLOYEES_TITLE = ALL + " Employees";

	public static final String PROFILE_TITLE = "Profile";
	public static final String EDIT_PROFILE_TITLE = EDIT + " Profile";

	public static final String ADD_COMPANY_TITLE = ADD + " Company";
	public static final String ALL_COMPANY_TITLE = ALL + " Companies";
	public static final String EDIT_COMPANY_TITLE = EDIT + " Company";
	public static final String DELETE_COMPANY_TITLE = DELETE + " Company";

	public static final String ADD_OFFICE_TITLE = ADD + " Office";
	public static final String EDIT_OFFICE_TITLE = EDIT + " Office";
	public static final String DELETE_OFFICE_TITLE = DELETE + " Office";
	public static final String ALL_OFFICE_TITLE = ALL + " Offices";

	public static final String ADD_SERVICE_TITLE = ADD + " Service";
	public static final String EDIT_SERVICE_TITLE = EDIT + " Service";
	public static final String DELETE_SERVICE_TITLE = DELETE + " Service";
	public static final String ALL_SERVICE_TITLE = ALL + " Service";

	public static final String ADD_RESERVATION_TITLE = ADD + " Reservation";
	public static final String EDIT_RESERVATION_TITLE = EDIT + " Reservation";
	public static final String REJECT_RESERVATION_TITLE = RESERVATION_REJECT + " Reservation";
	public static final String CONFIRM_RESERVATION_TITLE = RESERVATION_CONFIRM + " Reservation";

	public static final String ALL_RESERVATIONS_TITLE = ALL + " Reservation";
	public static final String MY_RESERVATIONS_TITLE = "My Reservation";

	public static final String INDEX_TITLE = "Index";
	public static final String HOME_TITLE = "Home";
	public static final String ABOUT_TITLE = "About";
	public static final String CONTACT_TITLE = "Contact";
	public static final String ADMIN_SIDEBAR_TITLE = "Admin Sidebar";
	public static final String MODERATOR_SIDEBAR_TITLE = "Moderator Sidebar";
	public static final String REGISTER_TITLE = "Register";
	public static final String LOGIN_TITLE = "Login";
	public static final String PROMO_OFFERS_TITLE = "Promo Offers";

}

package com.petia.dollhouse.constants;

import java.time.format.DateTimeFormatter;

public interface Constants {
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

	public static final String COMPANY_NAME = "Dolls House";
	public static final String FORM_METHOD = "post";
	public static final String FORM_METHOD_EDIT = "put";
	public static final String FORM_METHOD_DELETE = "delete";
	// urls
	public static final String INDEX_ACTION = "/";
	public static final String HOME_ACTION = "/home";
	public static final String ABOUT_ACTION = "/about";
	public static final String CONTACT_ACTION = "/contact";
	public static final String ADMIN_SIDEBAR_ACTION = "/admin-sidebar";

	public static final String PROFILE_ACTION = "/users/profile";
	public static final String EDIT_PROFILE_ACTION = "/users/edit-profile/";
	public static final String REGISTER_FORM_ACTION = "/users/register";
	public static final String LOGIN_FORM_ACTION = "/users/login";
	public static final String ADD_EMPLOYEE_ACTION = "/users/add-employee";
	public static final String EDIT_EMPLOYEE_ACTION = "/users/edit-employee/";
	public static final String DELETE_EMPLOYEE_ACTION = "/users/delete-employee/";
	public static final String AlL_EMLOYEES_ACTION = "/users/all-employees";
	public static final String SET_USER_ACTION = "/users/set-user/";
	public static final String SET_MODERATOR_ACTION = "/users/set-moderator/";
	public static final String SET_ADMIN_ACTION = "/users/set-admin/";

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
	public static final String FETCH_AVAILABILITIES = SERVICE_CONTEXT + "/availabilities/";

	public static final String ADD_RESERVATION_ACTION = "/reservation/add-reservation";
	public static final String EDIT_RESERVATION_ACTION = "/reservation/edit-reservation/";
	public static final String DELETE_RESERVATION_ACTION = "/reservation/delete-reservation/";
	public static final String ALL_RESERVATIONS_ACTION = "/reservation/all-reservations";

	public static final String AlL_RESERVATION_ACTION = "/reservation/all";
	public static final String LOGOUT_FORM_ACTION = "/logout?login";

	// pages name
	public static final String REGISTER_PAGE = "/register";
	public static final String LOGIN_PAGE = "/login";
	public static final String INDEX_PAGE = "index";
	public static final String HOME_PAGE = "home";
	public static final String ABOUT_PAGE = "about";
	public static final String CONTACT_PAGE = "contact";
	public static final String ADMIN_SIDEBAR_PAGE = "admin-sidebar";

	public static final String ADD_EMPLOYEE_PAGE = "/users/add-employee";
	public static final String EDIT_EMPLOYEE_PAGE = "/users/edit-employee";
	public static final String DELETE_EMPLOYEE_PAGE = "/users/delete-employee";
	public static final String ALL_EMPLOYEES_PAGE = "/users/all-employees";
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
	public static final String ALL_RESERVATIONS_PAGE = "/reservation/all-reservations";

	// Error Massages
	public static final String USERNAME_ERROR_MESSAGE = "Username not found!";
	public static final String PASSWORD_ERROR_MESSAGE = "Incorrect password!";
	public static final String ID_ERROR_MESSAGE = "Incorrect id!";
	public static final String ERROR_MESSAGE = "The searched item not found!";

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
	public static final String DELETE_RESERVATION_TITLE = DELETE + " Reservation";
	public static final String ALL_RESERVATIONS_TITLE = ALL + " Reservation";

	public static final String INDEX_TITLE = "Index";
	public static final String HOME_TITLE = "Home";
	public static final String ABOUT_TITLE = "About";
	public static final String CONTACT_TITLE = "Contact";
	public static final String ADMIN_SIDEBAR_TITLE = "Admin Sidebar";
	public static final String REGISTER_TITLE = "Register";
	public static final String LOGIN_TITLE = "Login";
}

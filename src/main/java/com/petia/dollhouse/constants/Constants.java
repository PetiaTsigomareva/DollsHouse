package com.petia.dollhouse.constants;

public class Constants {

    public static final String FORM_METHOD = "post";
    public static final String FORM_METHOD_EDIT = "put";
    public static final String FORM_METHOD_DELETE = "delete";
    // urls
    public static final String INDEX_ACTION = "/";
    public static final String HOME_ACTION = "/home";
    public static final String ABOUT_ACTION = "/about";
    public static final String CONTACT_ACTION = "/contact";

    public static final String ADD_COMPANY_ACTION = "/company/add-company";
    public static final String EDIT_COMPANY_ACTION = "/company/edit-company/";
    public static final String DELETE_COMPANY_ACTION = "/company/delete-company/";
    public static final String ALL_COMPANY_ACTION = "/company/all-companies";

    public static final String ADD_OFFICE_ACTION = "/office/add-office";
    public static final String EDIT_OFFICE_ACTION = "/office/edit-office";
    public static final String DELETE_OFFICE_ACTION = "/office/delete-office";
    public static final String ALL_OFFICE_ACTION = "/office/all-offices";

    public static final String PROFILE_ACTION = "/users/profile";
    public static final String EDIT_PROFILE_ACTION = "/users/edit-profile";
    public static final String REGISTER_FORM_ACTION = "/users/register";
    public static final String LOGIN_FORM_ACTION = "/users/login";
    public static final String AlL_USER_ACTION = "/users/all";
    public static final String SET_USER_ACTION = "/users/set-user/";
    public static final String SET_MODERATOR_ACTION = "/users/set-moderator/";
    public static final String SET_ADMIN_ACTION = "/users/set-admin/";

    public static final String AlL_RESERVATION_ACTION = "/reservation/all";
    public static final String LOGOUT_FORM_ACTION = "/logout?login";

    // pages name
    public static final String REGISTER_PAGE = "/register";
    public static final String LOGIN_PAGE = "/login";
    public static final String ALL_USER_PAGE = "/all-users";
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


    // Error Massages
    public static final String USERNAME_ERROR_MESSAGE = "Username not found!";
    public static final String PASSWORD_ERROR_MESSAGE = "Incorrect password!";
    public static final String ID_ERROR_MESSAGE = "Incorrect id!";
    public static final String COMPANY_ERROR_MESSAGE = "Company Info not found!";
}

package dev.anirban.todo.constants;


public class UrlConstants {

    // Authentication Endpoints
    public static final String REGISTER_USER = "/register";
    public static final String LOGIN_USER = "/login";
    public static final String VERIFY_USER = "/verify";

    // User Endpoints
    public static final String DELETE_USER = "/users";

    // To Do Endpoints
    public static final String CREATE_TODO = "/todos";
    public static final String FIND_TODO_QUERY = "/todos";
    public static final String UPDATE_TODO = "/todos";
    public static final String DELETE_TODO_BY_ID = "/todos/{id}";
}

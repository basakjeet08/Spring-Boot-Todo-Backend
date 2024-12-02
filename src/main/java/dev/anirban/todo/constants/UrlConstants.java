package dev.anirban.todo.constants;


public class UrlConstants {

    // User Endpoints
    public static final String CREATE_USER = "/users";
    public static final String FIND_ALL_USER = "/users";
    public static final String FIND_USER_BY_ID = "/users/{id}";
    public static final String DELETE_USER_BY_ID = "/users/{id}";

    // Category Endpoints
    public static final String CREATE_CATEGORY = "/categories";
    public static final String FIND_ALL_CATEGORY = "/categories";
    public static final String FIND_CATEGORY_BY_ID = "/categories/{id}";
    public static final String DELETE_CATEGORY_BY_ID = "/categories/{id}";

    // To Do Endpoints
    public static final String CREATE_TODO = "/todos";
    public static final String FIND_ALL_TODO = "/todos";
    public static final String FIND_TODO_BY_ID = "/todos/{id}";
    public static final String DELETE_TODO_BY_ID = "/todos/{id}";
}

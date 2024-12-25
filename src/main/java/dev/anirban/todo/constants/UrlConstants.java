package dev.anirban.todo.constants;


public class UrlConstants {

    // Authentication Endpoints
    public static final String REGISTER_USER = "/register";
    public static final String LOGIN_USER = "/login";

    // User Endpoints
    public static final String FIND_USER = "/users";
    public static final String DELETE_USER = "/users";

    // Category Endpoints
    public static final String CREATE_CATEGORY = "/categories";
    public static final String FIND_CATEGORY_BY_USER_ID = "/categories";
    public static final String DELETE_CATEGORY_BY_ID = "/categories/{id}";

    // To Do Endpoints
    public static final String CREATE_TODO = "/todos";
    public static final String FIND_TODO_BY_ID = "/todos/{id}";
    public static final String FIND_TODO_QUERY = "/todos";
    public static final String DELETE_TODO_BY_ID = "/todos/{id}";

    // Checkpoint Endpoints
    public static final String CREATE_CHECKPOINT = "/checkpoints";
    public static final String FIND_CHECKPOINT_BY_ID = "/checkpoints/{id}";
    public static final String FIND_CHECKPOINT_QUERY = "/checkpoints";
    public static final String DELETE_CHECKPOINT_BY_ID = "/checkpoints/{id}";
}

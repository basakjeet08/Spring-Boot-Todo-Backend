package dev.anirban.todo.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String id) {
        super("User with ID : " + id + " is not found !!");
    }
}
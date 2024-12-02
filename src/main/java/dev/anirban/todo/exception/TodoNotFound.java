package dev.anirban.todo.exception;

public class TodoNotFound extends RuntimeException {
    public TodoNotFound(String id) {
        super("Todo with id : " + id + " is not found !!");
    }
}
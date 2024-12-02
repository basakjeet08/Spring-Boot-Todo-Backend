package dev.anirban.todo.exception;

public class CategoryNotFound extends RuntimeException {
    public CategoryNotFound(String id) {
        super("Category with the id : " + id + " is not found !!");
    }
}

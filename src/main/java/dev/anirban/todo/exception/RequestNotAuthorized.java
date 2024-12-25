package dev.anirban.todo.exception;

public class RequestNotAuthorized extends RuntimeException {
    public RequestNotAuthorized() {
        super("This request is not authorized by the Backend server");
    }
}

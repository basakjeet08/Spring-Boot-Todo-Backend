package dev.anirban.todo.exception;

public class CheckpointNotFound extends RuntimeException {
    public CheckpointNotFound(String id) {
        super("Checkpoint with id : " + id + " is not found !!");
    }
}
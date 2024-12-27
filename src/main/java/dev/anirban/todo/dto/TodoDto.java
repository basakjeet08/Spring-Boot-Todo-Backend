package dev.anirban.todo.dto;

import dev.anirban.todo.entity.Todo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private String id;
    private String title;
    private String description;
    private Todo.TodoStatus status;
    private String category;
    private List<CheckpointDto> checkpoints;
}

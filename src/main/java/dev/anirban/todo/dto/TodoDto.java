package dev.anirban.todo.dto;

import dev.anirban.todo.entity.Todo;
import lombok.*;


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
}

package dev.anirban.todo.dto;

import lombok.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private String id;
    private String description;
    private Boolean isCompleted;
}

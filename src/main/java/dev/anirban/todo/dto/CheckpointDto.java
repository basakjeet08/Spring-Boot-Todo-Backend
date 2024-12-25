package dev.anirban.todo.dto;

import dev.anirban.todo.entity.Checkpoint;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckpointDto {
    private String id;
    private String description;
    private Checkpoint.CheckpointStatus status;
    private String todo;
}

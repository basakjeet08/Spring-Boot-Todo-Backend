package dev.anirban.todo.entity;

import dev.anirban.todo.dto.TodoDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TODO_DB")
public class Todo {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    public TodoDto toTodoDto() {
        return TodoDto
                .builder()
                .id(id)
                .description(description)
                .isCompleted(isCompleted)
                .build();
    }
}
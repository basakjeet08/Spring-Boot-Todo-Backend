package dev.anirban.todo.entity;


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
@Table(name = "CHECKPOINT_DB")
public class Checkpoint {

    enum CheckpointStatus {
        PENDING, STARTED, COMPLETED
    }

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CheckpointStatus status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    private User createdBy;

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    private Todo todo;
}

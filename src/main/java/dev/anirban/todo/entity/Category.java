package dev.anirban.todo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.anirban.todo.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORY_DB")
public class Category {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    @JoinColumn(nullable = false)
    private User createdBy;

    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Set<Todo> todoList;

    public void addTodo(Todo todo) {
        todoList.add(todo);
        todo.setCategory(this);
    }

    public CategoryDto toCategoryDto() {
        return CategoryDto
                .builder()
                .id(id)
                .name(name)
                .description(description)
                .UserUid(createdBy.getUid())
                .build();
    }
}

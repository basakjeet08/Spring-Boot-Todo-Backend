package dev.anirban.todo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_DB")
public class User implements UserDetails {

    public enum UserRole {
        USER
    }

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String uid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roles", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole roles;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(
            mappedBy = "createdBy",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Set<Category> categoriesCreated;

    @OneToMany(
            mappedBy = "createdBy",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Set<Todo> todosCreated;

    @OneToMany(
            mappedBy = "createdBy",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Set<Checkpoint> checkpointCreated;

    public void addCategory(Category category) {
        categoriesCreated.add(category);
        category.setCreatedBy(this);
    }

    public void addTodo(Todo todo) {
        todosCreated.add(todo);
        todo.setCreatedBy(this);
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        checkpointCreated.add(checkpoint);
        checkpoint.setCreatedBy(this);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
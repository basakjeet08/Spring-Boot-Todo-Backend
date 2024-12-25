package dev.anirban.todo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String uid;
    private String name;
    private String username;
    private String email;
}
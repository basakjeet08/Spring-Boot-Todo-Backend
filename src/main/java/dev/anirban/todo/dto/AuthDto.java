package dev.anirban.todo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String name;
    private String username;
    private String password;
}

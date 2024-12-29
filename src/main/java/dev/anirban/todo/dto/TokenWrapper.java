package dev.anirban.todo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenWrapper {
    private String token;
    private String refreshToken;
}
package com.example.rebrainauth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginDto {
    private String email;
    private String jwtToken;
}

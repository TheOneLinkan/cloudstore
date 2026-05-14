package se.jensen.linkan.userorderservice.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
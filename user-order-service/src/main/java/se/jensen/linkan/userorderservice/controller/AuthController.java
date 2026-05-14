package se.jensen.linkan.userorderservice.controller;

import org.springframework.web.bind.annotation.*;
import se.jensen.linkan.userorderservice.dto.LoginRequest;
import se.jensen.linkan.userorderservice.dto.RegisterRequest;
import se.jensen.linkan.userorderservice.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return "User created";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/test")
    public String test() {
        return "You are authenticated";
    }
}

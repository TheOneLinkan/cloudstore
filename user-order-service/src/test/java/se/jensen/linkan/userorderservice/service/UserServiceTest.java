package se.jensen.linkan.userorderservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.linkan.userorderservice.dto.RegisterRequest;
import se.jensen.linkan.userorderservice.model.User;
import se.jensen.linkan.userorderservice.repository.UserRepository;
import se.jensen.linkan.userorderservice.security.JwtUtil;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUser() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("test");
        request.setPassword("1234");

        when(passwordEncoder.encode("1234"))
                .thenReturn("encoded");

        userService.register(request);

        verify(userRepository, times(1))
                .save(any(User.class));
    }
}
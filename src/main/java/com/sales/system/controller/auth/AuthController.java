package com.sales.system.controller.auth;

import com.sales.system.dto.auth.LoginRequestDTO;
import com.sales.system.dto.auth.LoginResponseDTO;
import com.sales.system.dto.user.UserRegisterRequestDTO;
import com.sales.system.dto.user.UserResponseDTO;
import com.sales.system.service.auth.AuthService;
import com.sales.system.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        String token = authService.authenticate(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}

package com.sales.system.controller.auth;

import com.sales.system.dto.user.UserRegisterRequestDTO;
import com.sales.system.dto.user.UserResponseDTO;
import com.sales.system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserRegisterRequestDTO dto
    ) {
        return ResponseEntity.ok(userService.register(dto));
    }
}

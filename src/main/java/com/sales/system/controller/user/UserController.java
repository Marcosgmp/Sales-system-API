package com.sales.system.controller.user;

import com.sales.system.dto.user.UserResponseDTO;
import com.sales.system.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.findByEmail(email));
    }
}

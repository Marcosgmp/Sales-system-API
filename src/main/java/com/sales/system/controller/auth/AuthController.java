package com.sales.system.controller.auth;

import com.sales.system.dto.auth.LoginRequestDTO;
import com.sales.system.dto.auth.LoginResponseDTO;
import com.sales.system.dto.user.UserRegisterRequestDTO;
import com.sales.system.dto.user.UserResponseDTO;
import com.sales.system.service.auth.AuthService;
import com.sales.system.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Auth",
        description = "Autenticação e registro de usuários"
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(
            summary = "Registrar usuário",
            description = "Cria um novo usuário no sistema",
            security = {}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @Operation(
            summary = "Login",
            description = "Autentica o usuário e retorna um token JWT",
            security = {}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        String token = authService.authenticate(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}

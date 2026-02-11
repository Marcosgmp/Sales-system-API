package com.sales.system.controller.admin;

import com.sales.system.entity.Roles;
import com.sales.system.service.user.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Admin - Roles",
        description = "Gerenciamento de papéis (roles) do sistema"
)
@RestController
@RequestMapping("/api/admin/roles")
public class AdminRolesController {

    private final RolesService rolesService;

    public AdminRolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @Operation(
            summary = "Criar role",
            description = "Cria um novo papel de acesso no sistema (ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) {
        Roles savedRole = rolesService.createRole(role.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @Operation(
            summary = "Listar roles",
            description = "Lista todos os papéis de acesso cadastrados (ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping
    public ResponseEntity<List<Roles>> listAllRoles() {
        return ResponseEntity.ok(rolesService.listAll());
    }
}

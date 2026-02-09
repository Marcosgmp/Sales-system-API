package com.sales.system.controller.admin;

import com.sales.system.entity.Roles;
import com.sales.system.service.user.RolesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
public class AdminRolesController {

    private final RolesService rolesService;

    public AdminRolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @PostMapping
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) {
        Roles savedRole = rolesService.createRole(role.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @GetMapping
    public ResponseEntity<List<Roles>> listAllRoles() {
        return ResponseEntity.ok(rolesService.listAll());
    }
}

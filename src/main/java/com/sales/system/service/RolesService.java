package com.sales.system.service;

import com.sales.system.entity.Roles;
import com.sales.system.repository.RolesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RolesService {

    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Transactional
    public Roles create(String name) {
        return rolesRepository.findByName(name)
                .orElseGet(() -> {
                    Roles role = new Roles();
                    role.setName(name);
                    return rolesRepository.save(role);
                });
    }

    public List<Roles> listAll() {
        return rolesRepository.findAll();
    }

    public Roles findByName(String name) {
        return rolesRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));
    }
}

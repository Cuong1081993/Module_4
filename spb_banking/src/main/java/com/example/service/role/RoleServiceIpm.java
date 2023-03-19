package com.example.service.role;

import com.example.model.Role;
import com.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RoleServiceIpm implements IRoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role getById(Long id) {
        return null;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleted(Role role) {

    }

    @Override
    public void deletedById(Long id) {

    }

    @Override
    public boolean existByIdEqual(Long id) {
        return false;
    }

    @Override
    public Role findByName(String name) {
        return null;
    }
}

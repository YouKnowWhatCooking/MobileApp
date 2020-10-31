package com.controller;

import com.entity.Bonus;
import com.entity.Role;
import com.exception.ResourceNotFoundException;
import com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }


    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable("id") int ID) {
        return this.roleRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id :" + ID));
    }


    @PostMapping
    public Role createRole(@RequestBody Role role){
        Role newRole = new Role(role.getTitle());
        return this.roleRepository.save(newRole);
    }

    @PutMapping("/{id}")
    public Role updateRole(@RequestBody Role role, @PathVariable("id") int ID){
        Role existingRole = this.roleRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id :" + ID));
        existingRole.setTitle(role.getTitle());
        return this.roleRepository.save(existingRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bonus> deleteRole(@PathVariable("id") int ID) {
        Role existingRole = this.roleRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id :" + ID));
        this.roleRepository.delete(existingRole);
        return ResponseEntity.ok().build();
    }
}

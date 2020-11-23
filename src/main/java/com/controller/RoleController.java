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
    public ResponseEntity<?> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return ResponseEntity.ok(roleList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable("id") int ID) {
        Role role = roleRepository.findById(ID);
        return ResponseEntity.ok(role);
    }


    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role){
        Role newRole = new Role(role.getTitle());
        this.roleRepository.save(newRole);
        return ResponseEntity.ok("Success");
    }

    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody Role role){
        Role existingRole = this.roleRepository.findById(role.getId());
        existingRole.setTitle(role.getTitle());
        this.roleRepository.save(existingRole);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") int ID) {
        Role existingRole = this.roleRepository.findById(ID);
        this.roleRepository.delete(existingRole);
        return ResponseEntity.ok("Success");
    }
}

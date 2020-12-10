package com.controller;

import com.entity.Role;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return ResponseEntity.ok(roleList);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserRoles(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Role roleUser = roleRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Role roleAdmin = roleRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Set<Role> roleList = user.getRole();
        if(roleList.contains(roleAdmin)){
            return ResponseEntity.ok(roleAdmin);
        } else return ResponseEntity.ok(roleUser);

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable("id") int ID) {
        Role role = roleRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        System.out.println(role);
        return ResponseEntity.ok(role);
    }


    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        Role newRole = new Role(role.getTitle());
        this.roleRepository.save(newRole);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody Role role) {
        Role existingRole = this.roleRepository.findById(role.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        existingRole.setTitle(role.getTitle());
        this.roleRepository.save(existingRole);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") int ID) {
        Role existingRole = this.roleRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        this.roleRepository.delete(existingRole);
        return ResponseEntity.ok().build();
    }
}

package com.controller;

import com.config.JwtTokenUtil;
import com.entity.Role;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.JwtRequest;
import com.payload.JwtResponse;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        boolean isAdmin = false;
        User user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role role2 = roleRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role2);
        if (user.getRole().containsAll(roles)) {
            isAdmin = true;
            return ResponseEntity.ok(new JwtResponse(token, isAdmin));
        }
        return ResponseEntity.ok(new JwtResponse(token, isAdmin));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
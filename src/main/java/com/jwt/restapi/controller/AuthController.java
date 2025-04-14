package com.jwt.restapi.controller;

import com.jwt.restapi.dto.LoginResponseDTO;
import com.jwt.restapi.dto.UserResponseDTO;
import com.jwt.restapi.entity.User;
import com.jwt.restapi.repository.UserRepository;
import com.jwt.restapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty() ||
                user.getPasswordRepeat() == null || user.getPasswordRepeat().trim().isEmpty() ||
                user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("All fields are required");
        }

        if (!user.getPassword().equals(user.getPasswordRepeat())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        if (user.getRole() == null || !user.getRole().equals(User.Role.ADMIN)) {
            user.setRole(User.Role.USER);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPasswordRepeat(null);

        User savedUser = userRepository.save(user);

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + savedUser.getRole().name());
        String token = jwtTokenProvider.generateToken(savedUser.getUsername(), Collections.singletonList(authority));

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().name()
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser == null || !bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + foundUser.getRole().name());
        String token = jwtTokenProvider.generateToken(foundUser.getUsername(), Collections.singletonList(authority));

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                foundUser.getId(),
                foundUser.getUsername(),
                foundUser.getEmail(),
                foundUser.getRole().name()
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/users")
    ResponseEntity<?>getAllUsers(){
        List<User> users=userRepository.findAll();
        List<UserResponseDTO> response= users.stream().map(user->
                new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole().name()
                )).toList();
        return ResponseEntity.ok(response);
    }



}
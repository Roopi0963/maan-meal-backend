package org.techtricks.maanmeal.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techtricks.maanmeal.config.JwtUtil;
import org.techtricks.maanmeal.dto.LoginRequest;
import org.techtricks.maanmeal.exceptions.FarmerNotFoundException;
import org.techtricks.maanmeal.models.Admin;
import org.techtricks.maanmeal.models.Farmer;
import org.techtricks.maanmeal.models.BaseUser;
import org.techtricks.maanmeal.models.User;
import org.techtricks.maanmeal.services.AdminService;
import org.techtricks.maanmeal.services.FarmerService;
import org.techtricks.maanmeal.services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;


    private final AdminService adminService;


    private final FarmerService farmerService;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, AdminService adminService, FarmerService farmerService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.adminService = adminService;
        this.farmerService = farmerService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws FarmerNotFoundException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<? extends BaseUser> authenticatedUser = authenticateUser(email, password);

        return authenticatedUser.map(user -> {
            String token = jwtUtil.generateToken(user.getEmail());
            String role = user.getRole().toString();
            String userId = user.getId().toString();

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", role);
            response.put("userId", userId);

            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Invalid username or password")));
    }

    private Optional<? extends BaseUser> authenticateUser(String email, String password) throws FarmerNotFoundException {
        Optional<User> user = userService.authenticate(email, password);
        if (user.isPresent()) return user;

        Optional<Admin> admin = adminService.authenticate(email, password);
        if (admin.isPresent()) return admin;

        Optional<Farmer> artisan = farmerService.authenticate(email, password);
        if (artisan.isPresent()) return artisan;
        return Optional.empty();
    }
    
}
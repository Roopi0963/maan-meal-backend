package org.techtricks.artisanPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techtricks.artisanPlatform.config.JwtUtil;
import org.techtricks.artisanPlatform.dto.LoginRequest;
import org.techtricks.artisanPlatform.models.BaseUser;
import org.techtricks.artisanPlatform.models.User;
import org.techtricks.artisanPlatform.services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private ArtisanService artisanService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<? extends BaseUser> authenticatedUser = authenticateUser(email, password);

        return authenticatedUser.map(user -> {
            String token = jwtUtil.generateToken(user.getEmail());
            String role = user.getRole().toString();

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", role);

            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Invalid username or password")));
    }

    private Optional<? extends BaseUser> authenticateUser(String email, String password) {
        Optional<User> user = userService.authenticate(email, password);
        if (user.isPresent()) return user;

//        Optional<Admin> admin = adminService.authenticate(email, password);
//        if (admin.isPresent()) return admin;
//
//        Optional<Artisan> artisan = artisanService.authenticate(email, password);
//        return artisan;

        return Optional.empty();
    }
}

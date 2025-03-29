package org.techtricks.artisanPlatform.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.User;
import org.springframework.http.ResponseEntity;
import org.techtricks.artisanPlatform.services.UserService;
import java.util.List;



@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.saveUser(user);
        return ResponseEntity.ok(registeredUser);
    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody User user) {
//        Optional<User> authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
//        return authenticatedUser.map(value -> {
//            String token = jwtUtil.generateToken(value.getEmail());
//            String role = value.getRole().toString();
//            // Create a response map
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//            response.put("role", role);
//
//            return ResponseEntity.ok(response);
//        }).orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Invalid username or password")));
//    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        try{
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getByUsername")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        try {
            User user =userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        try {
            boolean isDeleted = userService.deleteUser(email);
            if(isDeleted){
                return ResponseEntity.ok("User deleted successfully");
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User could not be deleted");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) throws UserNotFoundException {
        User updateUser = userService.updateUser(user);
        return ResponseEntity.ok(updateUser);
    }
}

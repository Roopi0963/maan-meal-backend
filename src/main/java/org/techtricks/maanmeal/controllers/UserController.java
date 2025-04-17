//package org.techtricks.maanmeal.controllers;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.techtricks.maanmeal.exceptions.UserNotFoundException;
//import org.techtricks.maanmeal.models.User;
//import org.springframework.http.ResponseEntity;
//import org.techtricks.maanmeal.repositories.UserRepository;
//import org.techtricks.maanmeal.services.UserService;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/api/user")
//public class UserController {
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//
//    public UserController(UserService userService, UserRepository userRepository) {
//        this.userService = userService;
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<User> registerUser(@RequestBody User user) {
//        User registeredUser = userService.saveUser(user);
//        return ResponseEntity.ok(registeredUser);
//    }
////
////    @PostMapping("/login")
////    public ResponseEntity<?> loginUser(@RequestBody User user) {
////        Optional<User> authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
////        return authenticatedUser.map(value -> {
////            String token = jwtUtil.generateToken(value.getEmail());
////            String role = value.getRole().toString();
////            // Create a response map
////            Map<String, String> response = new HashMap<>();
////            response.put("token", token);
////            response.put("role", role);
////
////            return ResponseEntity.ok(response);
////        }).orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Invalid username or password")));
////    }
//
//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//    @GetMapping("/count")
//    public ResponseEntity<Long> getUserCount(){
//        long count = userRepository.count();
//        return ResponseEntity.ok(count);
//    }
//    @GetMapping("/getByEmail")
//    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
//        try {
//            User user = userService.getUserByEmail(email);
//            return ResponseEntity.ok(user);
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/getByUsername")
//    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
//        try {
//            User user =userService.getUserByUsername(username);
//            return ResponseEntity.ok(user);
//        }catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/deleteAccount")
//    public ResponseEntity<?> deleteUser(@RequestParam String email) {
//        try {
//            boolean isDeleted = userService.deleteUser(email);
//            if(isDeleted){
//                return ResponseEntity.ok("User deleted successfully");
//            } else{
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User could not be deleted");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<?> updateUser(@RequestBody User user) throws UserNotFoundException {
//        User updateUser = userService.updateUser(user);
//        return ResponseEntity.ok(updateUser);
//    }
//}
package org.techtricks.maanmeal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtricks.maanmeal.exceptions.UserNotFoundException;
import org.techtricks.maanmeal.models.User;
import org.techtricks.maanmeal.repositories.UserRepository;
import org.techtricks.maanmeal.services.UserService;
import org.techtricks.maanmeal.services.EmailService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserController(UserService userService, UserRepository userRepository, EmailService emailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.saveUser(user);

        // Send welcome email
        String to = registeredUser.getEmail();
        String subject = "🎉 Welcome to MaanMeal!";
        String body = "Hi " + registeredUser.getUsername() + ",\n\n" +
                "Thank you for signing up with MaanMeal.\n" +
                "We're thrilled to have you onboard!\n\n" +
                "Regards,\nMaanMeal Team";

        emailService.sendMail(to, subject, body);

        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        long count = userRepository.count();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getByUsername")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        try {
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        try {
            boolean isDeleted = userService.deleteUser(email);
            if (isDeleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User could not be deleted");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) throws UserNotFoundException {
        User updatedUser = userService.updateUser(user);

        // Send email on profile update
        String to = updatedUser.getEmail();
        String subject = "📢 Your MaanMeal Profile was Updated!";
        String body = "Hi " + updatedUser.getUsername() + ",\n\n" +
                "Your profile details were updated successfully.\n\n" +
                "If this wasn't you, please contact our support.\n\n" +
                "Cheers,\nMaanMeal Team";

        emailService.sendMail(to, subject, body);

        return ResponseEntity.ok(updatedUser);
    }
}

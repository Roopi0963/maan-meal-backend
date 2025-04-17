package org.techtricks.maanmeal.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techtricks.maanmeal.exceptions.AdminAlreadyExistsExceptions;
import org.techtricks.maanmeal.models.Admin;
import org.techtricks.maanmeal.services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/signup")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) throws AdminAlreadyExistsExceptions {
        Admin registeredAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(registeredAdmin);
    }

    
}

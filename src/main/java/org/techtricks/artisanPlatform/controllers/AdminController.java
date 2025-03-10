package org.techtricks.artisanPlatform.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techtricks.artisanPlatform.exceptions.AdminAlreadyExistsExceptions;
import org.techtricks.artisanPlatform.models.Admin;
import org.techtricks.artisanPlatform.services.AdminService;

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

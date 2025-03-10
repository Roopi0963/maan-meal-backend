package org.techtricks.artisanPlatform.services;


import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.exceptions.AdminAlreadyExistsExceptions;
import org.techtricks.artisanPlatform.models.Admin;

import java.util.Optional;

@Service
public interface AdminService {

    public Optional<Admin> authenticate(String email, String password);

    public Admin saveAdmin(Admin admin) throws AdminAlreadyExistsExceptions;
}

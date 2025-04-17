package org.techtricks.maanmeal.services;


import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.exceptions.AdminAlreadyExistsExceptions;
import org.techtricks.maanmeal.models.Admin;

import java.util.Optional;

@Service
public interface AdminService {

    public Optional<Admin> authenticate(String email, String password);

    public Admin saveAdmin(Admin admin) throws AdminAlreadyExistsExceptions;
}

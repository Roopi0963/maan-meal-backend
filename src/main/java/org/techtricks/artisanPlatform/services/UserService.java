package org.techtricks.artisanPlatform.services;

import org.springframework.stereotype.Service;

import org.techtricks.artisanPlatform.exceptions.UserAlreadyExistsException;

import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {


    public List<User> getAllUsers();

    public User saveUser(User user) throws UserAlreadyExistsException;

    public Optional<User> authenticate(String email, String password);

    public User  getUserByEmail(String email) throws UserNotFoundException;

   public User getUserByUsername(String username) throws UserNotFoundException;

   boolean deleteUser(String email) throws UserNotFoundException;

}

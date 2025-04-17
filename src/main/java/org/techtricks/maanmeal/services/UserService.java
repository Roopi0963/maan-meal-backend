package org.techtricks.maanmeal.services;

import org.springframework.stereotype.Service;

import org.techtricks.maanmeal.exceptions.UserAlreadyExistsException;

import org.techtricks.maanmeal.exceptions.UserNotFoundException;
import org.techtricks.maanmeal.models.User;

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

   public User updateUser(User user) throws UserNotFoundException;

}

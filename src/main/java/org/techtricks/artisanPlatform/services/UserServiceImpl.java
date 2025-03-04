package org.techtricks.artisanPlatform.services;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.config.JwtUtil;

import org.techtricks.artisanPlatform.exceptions.UserAlreadyExistsException;
import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.User;
import org.techtricks.artisanPlatform.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent())
            throw new UserAlreadyExistsException("user is already present with:"+user.getEmail()+" .please try with different email");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }



    @Override
    public Optional<User> authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }


    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException("User not found by this "+email+" ,please try again with correct email");

        return optionalUser.get();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException("User not found by this "+username+" , please try again with username");

        return optionalUser.get();
    }

    @Override
    public boolean deleteUser(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }


}
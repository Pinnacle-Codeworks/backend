package com.markguiang.backend.user;

import com.markguiang.backend.base.exceptions.UniqueConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new UniqueConstraintViolationException(user.getEmail());
            }
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new UniqueConstraintViolationException(user.getUsername());
            }
        }
        return user;
    }
}
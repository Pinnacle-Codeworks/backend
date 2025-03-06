package com.markguiang.backend.user;

import com.markguiang.backend.exceptions.UniqueConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(@Valid User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new UniqueConstraintViolationException(user.getEmail());
            }
        }
        return user;
    }
}

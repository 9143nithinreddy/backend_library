package com.library.service;

import com.library.model.User;
import com.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register user
    public String registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username already exists";
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email already registered";
        }

        user.setRole("USER");
        userRepository.save(user);
        return "User registered successfully";
    }

    // Login user
    public String loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "Invalid username or password";
        }

        User user = optionalUser.get();
        if (password.equals(user.getPassword())) {
            return "Login successful as " + user.getRole();
        } else {
            return "Invalid username or password";
        }
    }

    // Create admin (if not exists)
    public void createDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@library.com");
            admin.setPassword("admin123"); // plain text
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Default admin created (username: admin, password: admin123)");
        }
    }
}

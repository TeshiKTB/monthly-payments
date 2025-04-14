package com.monthlypayments.service.service;

import com.monthlypayments.service.domain.auth.User;
import com.monthlypayments.service.domain.auth.UserRole;
import com.monthlypayments.service.domain.user.RegisterRequest;
import com.monthlypayments.service.exception.UsernameAlreadyExistsException;
import com.monthlypayments.service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(RegisterRequest request) {
        User u = getUserFromRequest(request);

        if (userRepository.existsByUsername(u.getUsername()) ) {
            throw new UsernameAlreadyExistsException(String.format("User '%s' already exists", u.getUsername()));
        }

        userRepository.save(u);
    }

    private User getUserFromRequest(RegisterRequest request) {
        User result = new User();
        result.setUsername(request.getUsername());
        result.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        result.setRole(request.getRole() == null ? UserRole.USER : request.getRole());

        return result;
    }
}

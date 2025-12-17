package com.example.userservice.service;

import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.model.UserPrincipal;
import com.example.userservice.model.User; // <-- entity User
import com.example.userservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  // logging
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" Attempting to load user by username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error(" User not found in database: {}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        log.info(" User found: {} (ID: {})", user.getUsername(), user.getId());
        log.debug("User roles: {}", user.getRoles());

        UserPrincipal userPrincipal = UserPrincipal.build(user);
        log.info(" UserPrincipal created for: {}", userPrincipal.getUsername());

        return userPrincipal;
    }
}

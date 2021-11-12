package com.restaurant.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.domain.User;
import com.restaurant.backend.repository.PasswordUserRepository;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordUserRepository passwordUserRepository;

    private User user;

    public JWTUserDetailsService() {
    }

    public JWTUserDetailsService(User user) {
        this.user = user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PasswordUser> maybeUser = passwordUserRepository.findByUsername(username);
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }

    public UserDetails loadUserByPin(int pin) {
        // TODO: Implement staffRepository and use it here instead of
        // passwordUserRepository
        /*
         * Optional<PasswordUser> maybeUser =
         * passwordUserRepository.findByUsername(pin); if (maybeUser.isPresent()) {
         * return maybeUser.get(); } else { throw new
         * UsernameNotFoundException(String.format("No user found with pin '%s'.",
         * pin)); }
         */
        return null;
    }

    public boolean isEnabled() {
        return user.isEnabled();
    }

}

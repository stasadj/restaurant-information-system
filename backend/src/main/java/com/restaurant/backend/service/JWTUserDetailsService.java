package com.restaurant.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.Optional;

import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.domain.User;
import com.restaurant.backend.repository.PasswordUserRepository;
import com.restaurant.backend.repository.StaffRepository;

@Service
@AllArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordUserRepository passwordUserRepository;

    @Autowired
    private StaffRepository staffRepository;

    private User user;

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

    public UserDetails loadUserByPin(int pin) throws UsernameNotFoundException {
        Optional<Staff> maybeUser = staffRepository.findByPin(pin);
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        } else {
            throw new UsernameNotFoundException(String.format("No user found with pin '%s'.", pin));
        }
    }

    public boolean isEnabled() {
        return user.isEnabled();
    }

}

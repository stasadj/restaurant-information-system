package com.restaurant.backend.service;

import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.requests.ChangePasswordDTO;
import com.restaurant.backend.repository.PasswordUserRepository;
import com.restaurant.backend.repository.StaffRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@Setter
public class JWTUserDetailsService implements UserDetailsService {

    private PasswordUserRepository passwordUserRepository;
    private StaffRepository staffRepository;

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

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

    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        if (authenticationManager == null) return false;
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, changePasswordDTO.getCurrentPassword()));

        PasswordUser user = (PasswordUser) loadUserByUsername(username);

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        passwordUserRepository.save(user);
        return true;
    }
}

package com.restaurant.backend.service;

import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.requests.ChangePasswordDTO;
import com.restaurant.backend.repository.PasswordUserRepository;
import com.restaurant.backend.repository.StaffRepository;

import org.hibernate.Filter;
import org.hibernate.Session;
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

import javax.persistence.EntityManager;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	private final EntityManager entityManager;
    private final PasswordUserRepository passwordUserRepository;
    private final StaffRepository staffRepository;

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public JWTUserDetailsService(PasswordUserRepository passwordUserRepository, StaffRepository staffRepository, EntityManager entityManager) {
        this.passwordUserRepository = passwordUserRepository;
        this.staffRepository = staffRepository;
        this.entityManager = entityManager;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", false);
        Optional<PasswordUser> maybeUser = passwordUserRepository.findByUsername(username);
		session.disableFilter("deletedItemFilter");
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }

    public UserDetails loadUserByPin(int pin) throws UsernameNotFoundException {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", false);
        Optional<Staff> maybeUser = staffRepository.findByPin(pin);
        session.disableFilter("deletedItemFilter");
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        } else {
            throw new UsernameNotFoundException(String.format("No user found with pin '%s'.", pin));
        }
    }

    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        if (authenticationManager == null)
            return false;
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, changePasswordDTO.getCurrentPassword()));

        PasswordUser user = (PasswordUser) loadUserByUsername(username);

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        passwordUserRepository.save(user);
        return true;
    }
}

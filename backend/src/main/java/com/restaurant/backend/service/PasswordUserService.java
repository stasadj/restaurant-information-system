package com.restaurant.backend.service;

import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.dto.requests.ChangePasswordDTO;
import com.restaurant.backend.dto.requests.ChangeUsernameDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.PasswordUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PasswordUserService {
    private final PasswordUserRepository passwordUserRepository;

    public List<PasswordUser> getAll() {
        return passwordUserRepository.findAll();
    }

    public PasswordUser findOne(Long id) throws NotFoundException {
        return passwordUserRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("No password user with id %d has been found", id)));
    }

    public PasswordUser create(PasswordUser passwordUser) throws BadRequestException {
        passwordUser.setId(null);
        passwordUser.setDeleted(false);
        Optional<PasswordUser> maybeUser = passwordUserRepository.findByUsername(passwordUser.getUsername());
        if (maybeUser.isPresent())
            throw new BadRequestException(String.format("Password user with username %s already exists.", passwordUser.getUsername()));
        return passwordUserRepository.save(passwordUser);
    }

    public PasswordUser update(UserDTO userDTO) throws NotFoundException {
        PasswordUser user = findOne(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return passwordUserRepository.save(user);
    }

    public void delete(Long id) throws NotFoundException {
        PasswordUser user = findOne(id);
        passwordUserRepository.delete(user);
    }

    public PasswordUser changeUsername(ChangeUsernameDTO changeUsernameDTO) throws NotFoundException, BadRequestException {
        Optional<PasswordUser> maybeUser = passwordUserRepository.findByUsername(changeUsernameDTO.getCurrentUsername());
        Optional<PasswordUser> maybeOtherUser = passwordUserRepository.findByUsername(changeUsernameDTO.getNewUsername());
        if (maybeUser.isEmpty())
            throw new NotFoundException("Current username does not exist.");
        if (maybeOtherUser.isPresent())
            throw new BadRequestException("New username is already in use.");
        PasswordUser user = maybeUser.get();
        user.setUsername(changeUsernameDTO.getNewUsername());
        passwordUserRepository.save(user);
        return user;
    }

    public PasswordUser changePassword(ChangePasswordDTO changePasswordDTO) throws NotFoundException, BadRequestException {
        PasswordUser user = findOne(changePasswordDTO.getUserId());
        if (!user.getPassword().equals(changePasswordDTO.getCurrentPassword()))
            throw new BadRequestException("Incorrect password.");
        user.setPassword(changePasswordDTO.getNewPassword());
        passwordUserRepository.save(user);
        return user;
    }
}

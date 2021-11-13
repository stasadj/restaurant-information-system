package com.restaurant.backend.controller;

import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.repository.PasswordUserRepository;
import com.restaurant.backend.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/helloworld", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelloWorldController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

    private PasswordUserRepository passwordUserRepository;
    private StaffRepository staffRepository;

    @ResponseBody
    @GetMapping("/")
    public String index() {
        LOG.info("Client requested the index method.");
        return "Hello world";
    }

    @ResponseBody
    @GetMapping("/admin-test")
    public String adminTest() {
        LOG.info("Client requested the adminTest method.");
        Optional<PasswordUser> user = passwordUserRepository.findByUsername("jeff.goldblum");
        StringBuilder sb = new StringBuilder();
        sb.append("User jeff.goldblum was").append(user.isPresent() ? " " : " not ").append("found.");
        user.ifPresent(passwordUser -> sb.append(" Here is his phone number: ").append(passwordUser.getPhoneNumber()));
        return sb.toString();
    }

    @ResponseBody
    @GetMapping("/waiter-test")
    public String waiterTest() {
        LOG.info("Client requested the waiterTest method.");
        Optional<Staff> user = staffRepository.findByPin(1234);
        StringBuilder sb = new StringBuilder();
        sb.append("User 1234 was").append(user.isPresent() ? " " : " not ").append("found.");
        user.ifPresent(staff -> sb.append(" Here is his monthly wage: ").append(staff.getMonthlyWage()));
        return sb.toString();
    }

}
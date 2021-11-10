package com.restaurant.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;

import java.util.Optional;

import com.restaurant.backend.domain.User;
import com.restaurant.backend.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/helloworld", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelloWorldController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

    private UserRepository userRepository;

    @ResponseBody
    @GetMapping("/")
    public String index() {
        LOG.info("Client requested the index method.");
        String res = "Hello world";
        return res;
    }

    @ResponseBody
    @GetMapping("/user-test")
    public String userTest() {
        LOG.info("Client requested the userTest method.");
        Optional<User> user = userRepository.findByUsername("jeff.goldblum");
        StringBuilder sb = new StringBuilder();
        sb.append("User jeff.goldblum was" + (user.isPresent() ? " " : " not ") + "found.");
        if (user.isPresent()) {
            sb.append(" Here is his phone number: " + user.get().getPhoneNumber());
        }
        return sb.toString();
    }

}
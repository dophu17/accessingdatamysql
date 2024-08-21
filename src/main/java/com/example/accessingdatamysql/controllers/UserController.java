package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.models.User;
import com.example.accessingdatamysql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/add")
    public User addNewUser (@RequestParam String name, @RequestParam String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    @PutMapping(path = "/update/{id}")
    public User updateUser (@PathVariable int id, @RequestParam String name, @RequestParam String email) {
        User user = userRepository.findById(id).get();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    @DeleteMapping(path="/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser (@PathVariable int id) {
        userRepository.deleteById(id);
    }
}
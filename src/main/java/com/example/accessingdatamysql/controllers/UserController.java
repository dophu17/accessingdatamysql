package com.example.accessingdatamysql.controllers;

import com.example.accessingdatamysql.models.User;
import com.example.accessingdatamysql.repositories.UserRepository;
import com.example.accessingdatamysql.requestValidators.UserRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<String> addNewUser (@RequestBody @Valid UserRequest request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> errors= new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            String errorMsg= "";

            for(String key: errors.keySet()){
                errorMsg+= "Lỗi ở: " + key + ", lí do: " + errors.get(key) + "\n";
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(errorMsg);
        }

        //TODO: Thêm code gọi xuống service layer
        Logger log = null;
        log.info(request.toString());

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<User> updateUser (@PathVariable int id, @RequestParam String name, @RequestParam String email) {
        User user = userRepository.findById(id).get();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable int id) {
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }
}
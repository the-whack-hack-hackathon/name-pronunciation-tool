package com.hackathon.namepronunciationtool.controller;

import com.hackathon.namepronunciationtool.entity.UserDetails;
import com.hackathon.namepronunciationtool.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/users")
    public String createUser(@RequestBody UserDetails userDetails){
        userRepository.save(userDetails);
        return "SUCCESS";
    }

    @PutMapping("/api/users/{uid}")
    public String updateUser(@PathVariable("uid") String uid, @RequestBody UserDetails userDetails){
        UserDetails existing = userRepository.findByUid(uid);
        existing.setEmail(userDetails.getEmail());
        existing.setFirstName(userDetails.getFirstName());
        existing.setLastName(userDetails.getLastName());
        existing.setInsert(userDetails.getInsert());
        existing.setUserPronunciation(userDetails.getUserPronunciation());
        existing.setPreferredLastName(userDetails.getPreferredLastName());
        existing.setPreferredFirstName(userDetails.getPreferredFirstName());
        existing.setSystemPronunciation(userDetails.getSystemPronunciation());

        userRepository.save(existing);
        return "SUCCESS";
    }

    @DeleteMapping("/api/users/{uid}")
    public String deleteUser(@PathVariable("uid") String uid) {
        UserDetails existing = userRepository.findByUid(uid);
        userRepository.delete(existing);
        return "SUCCESS";
    }

    @GetMapping("/api/users/{uid}")
    public UserDetails fetchUser(@PathVariable("uid") String uid){
        return userRepository.findByUid(uid);
    }

    @GetMapping("/api/users")
    public List<UserDetails> fetchAllUsers(){
        Iterable<UserDetails> iterator = userRepository.findAll();
        List<UserDetails> userList = new ArrayList<>();
        iterator.forEach(userList::add);
        return userList;
    }
}

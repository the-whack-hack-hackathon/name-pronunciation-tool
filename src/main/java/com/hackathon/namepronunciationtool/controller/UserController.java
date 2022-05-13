package com.hackathon.namepronunciationtool.controller;

import com.hackathon.namepronunciationtool.entity.UserDetails;
import com.hackathon.namepronunciationtool.repo.UserYsqlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserYsqlRepository userYsqlRepository;

    @PutMapping("/api/user/create")
    public String createUser(@RequestBody UserDetails userDetails){
        userYsqlRepository.save(userDetails);
        return "SUCCESS";
    }

    @GetMapping("/api/user/list/{uid}")
    public UserDetails fetchUser(@PathVariable("uid") String uid){
        return userYsqlRepository.findByUid(uid);
    }

    @GetMapping("/api/user/list/all")
    public List<UserDetails> fetchAllUsers(){
        Iterable<UserDetails> iterator = userYsqlRepository.findAll();
        List<UserDetails> userList = new ArrayList<>();
        iterator.forEach(userList::add);
        return userList;
    }
}

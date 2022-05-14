package com.hackathon.namepronunciationtool.controller;

import com.hackathon.namepronunciationtool.entity.UserDetails;
import com.hackathon.namepronunciationtool.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        existing.setVoiceId(userDetails.getVoiceId());

        userRepository.save(existing);
        return "SUCCESS";
    }

    @DeleteMapping("/api/users/{uid}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable("uid") String uid) {
        try {
            long num = userRepository.deleteByUid(uid);
            LOGGER.debug("Num rows delete: {}", num);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/users/{uid}")
    public ResponseEntity<UserDetails> fetchUser(@PathVariable("uid") String uid){
        Optional<UserDetails> user = userRepository.findById(uid);
        return user.map(userDetails -> new ResponseEntity<>(userDetails, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/users")
    public List<UserDetails> fetchAllUsers(){
        Iterable<UserDetails> iterator = userRepository.findAll();
        List<UserDetails> userList = new ArrayList<>();
        iterator.forEach(userList::add);
        return userList;
    }
}

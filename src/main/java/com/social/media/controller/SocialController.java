package com.social.media.controller;

import com.social.media.models.SocialUser;
import com.social.media.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @GetMapping("social/users")
    public ResponseEntity<List<SocialUser>> getUsers(){
        return new ResponseEntity<>(socialService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("social/users")
    public ResponseEntity<SocialUser> saveUser(@RequestBody SocialUser user){
        return new ResponseEntity<>(socialService.saveUser(user), HttpStatus.CREATED);
    }
    @DeleteMapping("social/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        socialService.deleteUser(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.CREATED);
    }
}

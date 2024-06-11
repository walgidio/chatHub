package com.walgi.chatHub.controller;

import com.walgi.chatHub.model.User;
import com.walgi.chatHub.service.UserService;
import com.walgi.chatHub.zookeeper.ZooKeeperClient;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/best-server")
    public String getBestServer() throws KeeperException, InterruptedException, IOException {
        ZooKeeperClient zooKeeperClient = new ZooKeeperClient();
        return zooKeeperClient.selectBestServer();
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }
}
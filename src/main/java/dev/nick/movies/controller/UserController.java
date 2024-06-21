package dev.nick.movies.controller;

import java.util.*;

import dev.nick.movies.model.User;
import dev.nick.movies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Optional<User>> findByUsername(@RequestParam String username) {
        return new ResponseEntity<>(userService.findUsername(username), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> payload) {
        try {
            userService.createUser(payload.get("username"), payload.get("password"), payload.get("emailAddress"), payload.get("nickname"));
            return ResponseEntity.status(HttpStatus.OK).body("User creates successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<User>> userLogin(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(userService.userLogin(payload.get("username"), payload.get("password")), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<User>> updateUser(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(userService.updateUser(payload.get("username"), payload.get("nickname"),
                payload.get("twitter"), payload.get("avatar"), payload.get("notes")), HttpStatus.OK);
    }

    @PostMapping("/addWatchList")
    public ResponseEntity<Optional<User>> addWatchList(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(userService.addWatchList(payload.get("username"), payload.get("imdbId")), HttpStatus.OK);
    }

    @PostMapping("/removeWatchList")
    public ResponseEntity<Optional<User>> removeWatchList(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(userService.removeWatchList(payload.get("username"), payload.get("imdbId")), HttpStatus.OK);
    }
}

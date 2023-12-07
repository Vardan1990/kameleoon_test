package com.test.kameleoon_test.controllers;

import com.test.kameleoon_test.dto.CreateUserDto;
import com.test.kameleoon_test.dto.UpdateUserDto;
import com.test.kameleoon_test.entity.User;
import com.test.kameleoon_test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        log.info("user# create from dto {}", createUserDto);
        return ResponseEntity.ok(userService.createUser(createUserDto));

    }

    @PutMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestParam(name = "email") String email, @RequestBody @Valid UpdateUserDto updateUserDto) {
        log.info("user# update from dto {}", updateUserDto);
        return userService.updateUser(email, updateUserDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping(value = "/getUser")
    public ResponseEntity<?> getUser(@RequestParam(name = "email") String email) {
        log.info("get user by email {}", email);
        return userService.getUserByEmail(email).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam(name = "email") String email) {
        log.info("delete user by email {}", email);
        userService.deleteUser(email);
        return ResponseEntity.ok().build();
    }
}

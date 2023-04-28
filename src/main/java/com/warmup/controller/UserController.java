package com.warmup.controller;

import com.warmup.dto.UserDto;
import com.warmup.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto dto) {
        this.userService.createUser(dto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> loginUser(@RequestBody UserDto dto) {
        this.userService.loginUser(dto);
        return ResponseEntity.ok().body(userService.login(dto.getUserId(), dto.getPassword()));
    }

    @GetMapping("{id}")
    public UserDto readUser(@PathVariable("id") int id) {
        return this.userService.readUser(id);
    }

    @GetMapping("")
    public List<UserDto> readUserAll(){
        return this.userService.readUserAll();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(
            @PathVariable("id") int id,
            @RequestBody UserDto dto
    ){
        this.userService.updateUser(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@PathVariable("id") int id) {
        this.userService.deleteUser(id);
    }
}
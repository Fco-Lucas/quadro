package com.maia.quadro.controller;

import com.maia.quadro.dto.user.UserCreateDto;
import com.maia.quadro.dto.user.UserResponseDto;
import com.maia.quadro.mapper.UserMapper;
import com.maia.quadro.model.User;
import com.maia.quadro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto dto) {
        User user = userService.create(UserMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();

        return ResponseEntity.ok(users);
    }
}

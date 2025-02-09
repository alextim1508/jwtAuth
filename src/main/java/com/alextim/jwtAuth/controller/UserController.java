package com.alextim.jwtAuth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Примеры", description = "Примеры запросов авторизованным пользователей")
public class UserController {

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String hello() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Hello, " + username + " !";
    }
}
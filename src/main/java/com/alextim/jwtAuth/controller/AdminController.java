package com.alextim.jwtAuth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Примеры", description = "Примеры запросов пользователей с ролью ADMIN")
public class AdminController {

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    public String helloAdmin() {
        return "Hello, admin!";
    }
}

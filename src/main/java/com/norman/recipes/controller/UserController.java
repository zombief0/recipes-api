package com.norman.recipes.controller;

import com.norman.recipes.service.UserService;
import com.norman.recipes.service.UserStatus;
import com.norman.recipes.service.dto.MessageResponse;
import com.norman.recipes.service.dto.UtilisateurDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UtilisateurDto utilisateurDto){
        UserStatus userStatus = userService.saveUser(utilisateurDto);
        if (!userStatus.equals(UserStatus.SUCCES)){
            return ResponseEntity.badRequest().body(new MessageResponse(userStatus.name()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<?> activateUser(@PathVariable Long id){
        UserStatus userStatus = userService.activateUser(id);
        return ResponseEntity.ok(new MessageResponse(userStatus.name()));
    }
}

package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.entity.User;
import com.lambdacode.spring.boot.crud.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Endpoint para registro de usuário.
     * @return 
     */
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // Verifica se o e-mail já está cadastrado.
        Optional<User> existingUser = userService.getUsers()
                .stream()
                .filter(u -> u.getEmail().equals(user.getEmail()))
                .findFirst();

        if (existingUser.isPresent()) {
            response.put("status", "error");
            response.put("message", "Email já registrado!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Codifica a senha e salva o usuário.
        user.setPalavra_passe(passwordEncoder.encode(user.getPalavra_passe()));
        userService.addUser(user);

        response.put("status", "success");
        response.put("message", "Usuário registrado com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginRequest) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> user = userService.getUsers()
                .stream()
                .filter(u -> u.getEmail().equals(loginRequest.getEmail()))
                .findFirst();

        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPalavra_passe(), user.get().getPalavra_passe())) {
            response.put("status", "success");
            response.put("message", "Login bem-sucedido!");
            return ResponseEntity.ok(response);
        }

        response.put("status", "error");
        response.put("message", "Credenciais inválidas!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}

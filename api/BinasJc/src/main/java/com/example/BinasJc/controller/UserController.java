package com.example.BinasJc.controller;

import com.example.BinasJc.model.User;
import com.example.BinasJc.dto.LoginRequest;
import com.example.BinasJc.dto.RegisterRequest;
import com.example.BinasJc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Lice
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("YOU DID IT!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        // Validar se as senhas coincidem
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("As senhas não coincidem");
        }

        // Verificar se username já existe (insensível ao caso)
        if (userRepository.findByUserNameIgnoreCase(request.getUserName()) != null) {
            return ResponseEntity.badRequest().body("Nome de usuário já existe");
        }

        // Verificar se email já existe
        if (userRepository.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        // Criar novo usuário
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        // Procurar usuário pelo nome de usuário (insensível ao caso)
        User existingUser = userRepository.findByUserNameIgnoreCase(request.getUserName());

        if (existingUser == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }

        if (!existingUser.getPassword().equals(request.getPassword())) {
            return ResponseEntity.badRequest().body("Senha incorreta");
        }

        return ResponseEntity.ok(existingUser);
    }
}

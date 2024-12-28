package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.dto.UserDTO;
import com.lambdacode.spring.boot.crud.entity.User;
import com.lambdacode.spring.boot.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return "success add user";
    }

    @GetMapping("/get")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User userOptional = userService.getUser(id); // Usando Optional aqui

        if (userOptional != null) {
            return ResponseEntity.ok(userOptional); // Retorna o usuário se encontrado
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 se não encontrado
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("ID do usuário: " + id);
            System.out.println("Payload recebido: " + payload);

            User user = userService.getUser(id);
            if (user == null) {
                response.put("success", false);
                response.put("message", "Usuário não encontrado!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            System.out.println("Usuário encontrado: " + user);

            String nome = (String) payload.get("nome");
            String email = (String) payload.get("email");
            String oldPassword = (String) payload.get("oldPassword");
            String newPassword = (String) payload.get("palavra_passe");

            if (nome != null) user.setNome(nome);
            if (email != null) user.setEmail(email);

            if (oldPassword != null && newPassword != null) {
                System.out.println("Senha antiga recebida: " + oldPassword);
                System.out.println("Nova senha recebida: " + newPassword);

                if (!passwordEncoder.matches(oldPassword, user.getPalavra_passe())) {
                    response.put("success", false);
                    response.put("message", "A senha antiga está incorreta!");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                if (passwordEncoder.matches(newPassword, user.getPalavra_passe())) {
                    response.put("success", false);
                    response.put("message", "A nova senha não pode ser igual à antiga!");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                user.setPalavra_passe(passwordEncoder.encode(newPassword));
            }

            userService.updateUser(id, user);

            response.put("success", true);
            response.put("message", "Usuário atualizado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Erro no método updateUser: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Erro ao atualizar o usuário.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    
    @PutMapping("/update-password/{id}")
    public ResponseEntity<Map<String, Object>> updatePassword(@PathVariable Integer id, @RequestBody Map<String, String> passwordPayload) {
        Map<String, Object> response = new HashMap<>();

        // Captura senha antiga e nova senha
        String oldPassword = passwordPayload.get("oldPassword");
        String newPassword = passwordPayload.get("newPassword");

        User user = userService.getUser(id); // Retorna null se não encontrado

        if (user != null) { // Verifica se o usuário existe
            // Valida a senha antiga
            if (!passwordEncoder.matches(oldPassword, user.getPalavra_passe())) {
                response.put("success", false);
                response.put("message", "Senha antiga está incorreta!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Verifica se a nova senha é igual à antiga
            if (passwordEncoder.matches(newPassword, user.getPalavra_passe())) {
                response.put("success", false);
                response.put("message", "A nova senha não pode ser igual à antiga!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Atualiza a senha (criptografada)
            user.setPalavra_passe(passwordEncoder.encode(newPassword));
            userService.updateUser(id, user);

            response.put("success", true);
            response.put("message", "Senha atualizada com sucesso!");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Usuário não encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-name/{id}")
    public ResponseEntity<Map<String, Object>> updateName(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.updateName(id, userDTO);
            response.put("success", true);
            response.put("message", "Nome atualizado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao atualizar nome.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

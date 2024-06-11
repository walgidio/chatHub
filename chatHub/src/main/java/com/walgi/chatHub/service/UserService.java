package com.walgi.chatHub.service;

import com.walgi.chatHub.model.User;
import com.walgi.chatHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Verificar se o username já existe
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        // Calcular o hash da senha usando MD5
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedPassword = md.digest(user.getPassword().getBytes());
            user.setPassword(new String(hashedPassword)); // Armazenar o hash da senha
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        // Encontrar o usuário pelo username
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new RuntimeException("User not found");
        }
        // Calcular o hash da senha fornecida
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedPassword = md.digest(password.getBytes());
            String hashedPasswordString = new String(hashedPassword);
            // Verificar se a senha (hash) está correta
            if (!hashedPasswordString.equals(user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
        return user;
    }
}
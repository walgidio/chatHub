package com.walgi.chatHub.service;

import com.walgi.chatHub.model.User;
import com.walgi.chatHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    public User registerUser(User user) {
        // Verificar se o username já existe
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        // Encontrar o usuário pelo username
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new RuntimeException("User not found");
        }
        // Verificar se a senha está correta
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

}
package com.hemant.workflow_orchestrator.services;
import com.hemant.workflow_orchestrator.models.UserModel;
import com.hemant.workflow_orchestrator.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    UserService(UserRepository userrepository){
        this.userRepository = userrepository;
    }

    public UserModel createUser(UserModel user){
        return userRepository.save(user);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}

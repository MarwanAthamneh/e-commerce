package org.example.startupprjoect.Service.ServiceImpl;

import org.example.startupprjoect.Repository.UserRepository;
import org.example.startupprjoect.model.UserE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

   @Autowired

   private UserRepository userRepository;

    public Optional<UserE> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserE> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        UserE user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
}

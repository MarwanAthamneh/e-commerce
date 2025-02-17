package org.example.startupprjoect.Controller;

import org.example.startupprjoect.Service.ServiceImpl.UserServiceImpl;
import org.example.startupprjoect.model.UserE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{id}")
    public Optional<UserE> getUser(@PathVariable Long id){

        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<UserE> getAllUsers(){

        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully with id: " + id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found with id: " + id);
        }
    }
}

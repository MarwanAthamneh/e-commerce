package org.example.startupprjoect.Service;

import org.example.startupprjoect.model.UserE;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public void deleteUser(Long id);

    public List<UserE> getAllUsers();

    public Optional<UserE> getUserById(Long id);
}

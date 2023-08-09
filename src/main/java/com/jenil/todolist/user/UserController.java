package com.jenil.todolist.user;

import com.jenil.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Endpoint - to get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Endpoint - to get user by specific id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepo.findById(id).
                orElseThrow(() -> new UserNotFoundException("id: " + id));
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        userRepo.save(user);
        return ResponseEntity.ok(user);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        userRepo.deleteById(id);
    }
}

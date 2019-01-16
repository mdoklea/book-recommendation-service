package com.task.zenreadsservice.users;

import com.task.zenreadsservice.users.model.GenrePreference;
import com.task.zenreadsservice.users.model.User;
import com.task.zenreadsservice.users.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final static int MIN_NUMBER_OF_GENRE_PREFERENCES = 4;
    private  final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){

        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserGenresPreferences(@PathVariable final String username){

        return ResponseEntity.ok(userService.findUserByUsername(username).get());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> saveUser (@RequestBody final User user) {

        if ( userService.findUserById(user.getId()).isPresent() ||
                userService.findUserByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        if (user.getGenrePreferences() == null || user.getGenrePreferences().isEmpty() ||
                user.getGenrePreferences().size() < MIN_NUMBER_OF_GENRE_PREFERENCES ){
            return ResponseEntity.badRequest().body("Please select at least four genre preferences!");
        }

        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser (@RequestBody final User user) {

        if (user.getGenrePreferences().isEmpty() ||
                user.getGenrePreferences().size() < MIN_NUMBER_OF_GENRE_PREFERENCES ){
            return ResponseEntity.badRequest().body("Please select at least four genre preferences!");
        }

        return ResponseEntity.ok(userService.saveUser(user));
    }
}

package com.task.zenreadsservice.users.service;

import com.task.zenreadsservice.feedback.model.Feedback;
import com.task.zenreadsservice.users.model.GenrePreference;
import com.task.zenreadsservice.users.model.User;
import com.task.zenreadsservice.users.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private  final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(final User user) {

        if( findUserByUsername(user.getUsername()).isPresent() ){
            val userFromDb = findUserByUsername(user.getUsername());
            userFromDb.get().getGenrePreferences().clear();
            userFromDb.get().getGenrePreferences().addAll(updateGenrePreferences(userFromDb.get(), user));
            userFromDb.get().getFeedback().addAll(updateFeedback(userFromDb.get(), user));

            return  userRepository.saveAndFlush(userFromDb.get());
        }

        val updateUserFromDb =  userRepository.save(User
                .builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .genrePreferences(new HashSet<>())
                .feedback(new HashSet<>())
                .build());

        updateUserFromDb
                .getGenrePreferences()
                .addAll(updateGenrePreferences(updateUserFromDb, user));

        updateUserFromDb
                .getFeedback()
                .addAll(updateFeedback(updateUserFromDb, user));

        return userRepository.save(updateUserFromDb);

    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByUsername(final String username){
        return userRepository.findUsersByUsername(username);
    }

    public Optional<User> findUserById(final UUID id){
        return userRepository.findOneById(id);
    }

    private Set<GenrePreference> updateGenrePreferences(final User userFromDb, final User parsedUser){

        if(!Optional.ofNullable(parsedUser.getGenrePreferences()).isPresent()){
            return new HashSet<>();
        }

       return parsedUser.getGenrePreferences()
                .stream()
                .map(genrePreference -> new GenrePreference( userFromDb, genrePreference.getGenre() ))
               .collect(Collectors.toSet());
    }

    private Set<Feedback> updateFeedback(final User userFromDb, final User parsedUser){

        if(!Optional.ofNullable(parsedUser.getFeedback()).isPresent()){
            return new HashSet<>();
        }

        return parsedUser.getFeedback()
                .stream()
                .map(feedback1 -> new Feedback( userFromDb, feedback1.getBook(), feedback1.getRate() ))
                .collect(Collectors.toSet());
    }

}

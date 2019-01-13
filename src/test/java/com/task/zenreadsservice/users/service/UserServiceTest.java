package com.task.zenreadsservice.users.service;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.feedback.model.Feedback;
import com.task.zenreadsservice.feedback.model.Rating;
import com.task.zenreadsservice.genres.model.Genre;
import com.task.zenreadsservice.users.model.GenrePreference;
import com.task.zenreadsservice.users.model.User;
import com.task.zenreadsservice.users.repository.UserRepository;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private final int ZERO = 0;
    private final int NUMBER_OF_MIN_GENRE_PREFERENCE = 4;

    private final static Genre GENRE = Genre
            .builder()
            .id(UUID.randomUUID())
            .category("GENRES_NAME_1")
            .build();

    private final static Book BOOK = Book
            .builder()
            .id(UUID.randomUUID())
            .isbn("121242141423")
            .fileName("121242141423.jpg")
            .imageUrlPath(("121242141423.jpg"))
            .title("Xairetismata")
            .author("Kyrie Tade")
            .genre(GENRE)
            .build();

    private final static Set<GenrePreference> GENRE_PREFERENCES = new HashSet<GenrePreference>(Arrays
            .asList(GenrePreference
                    .builder()
                    .user(null)
                    .genre(GENRE)
                    .build()));

    private final static User USER = User
            .builder()
            .username("name_surname_1")
            .firstName("Name")
            .lastName("Surname_1")
            .build();

    private final static User USER_DB = User
            .builder()
            .username("name_surname_1")
            .firstName("Name")
            .lastName("Surname_1")
            .genrePreferences(new HashSet<>())
            .feedback(new HashSet<>())
            .build();


    private  final static User USER_WITH_GENRE_PREFERENCES = User
            .builder()
            .username("name_surname_1")
            .firstName("Name")
            .lastName("Surname_1")
            .genrePreferences(GENRE_PREFERENCES)
            .feedback(new HashSet<>())
            .build();

    private final static Set<Feedback> FEEDBACK = new HashSet<>(Arrays
            .asList(new Feedback(USER, BOOK, Rating.LIKE)));

    private  final static User USER_WITH_GENRE_PREFERENCES_AND_FEEDBACK = User
            .builder()
            .username("name_surname_1")
            .firstName("Name")
            .lastName("Surname_1")
            .genrePreferences(GENRE_PREFERENCES)
            .feedback(FEEDBACK)
            .build();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        given(userRepository
                .findUsersByUsername(any()))
                .willReturn(Optional.of(USER_DB));

    }

    @Test
    public void save_withNoGenrePreferencesAndNoFeedback_savesNewUserWithNoGenrePreferencesAndNoFeedback() {
        given(userRepository.saveAndFlush(USER)).willReturn(USER_DB);

        val actual = userService.saveUser(USER);

        assertEquals(USER.getUsername(), actual.getUsername());
        assertEquals(actual.getGenrePreferences().isEmpty(), Boolean.TRUE);
        assertEquals(actual.getFeedback().isEmpty(), Boolean.TRUE);
    }

    @Test
    public void save_withGenrePreferencesNoFeedback_savesNewUserWithGenrePreferencesAndNoFeedback() {
        given(userRepository.saveAndFlush(USER)).willReturn(USER_WITH_GENRE_PREFERENCES);

        val actual = userService.saveUser(USER);

        assertEquals(USER_WITH_GENRE_PREFERENCES.getUsername(), actual.getUsername());
        assertEquals(actual.getGenrePreferences().size(), GENRE_PREFERENCES.size());
        assertEquals(actual.getFeedback().size(), ZERO);

    }

    @Test
    public void save_withGenrePreferencesAndFeedback_savesNewUserWithGenrePreferencesAndFeedback() {
        given(userRepository.saveAndFlush(USER)).willReturn(USER_WITH_GENRE_PREFERENCES_AND_FEEDBACK);

        val actual = userService.saveUser(USER);

        assertEquals(USER_WITH_GENRE_PREFERENCES_AND_FEEDBACK.getUsername(), actual.getUsername());
        assertEquals(actual.getGenrePreferences().size(), GENRE_PREFERENCES.size());
        assertEquals(actual.getFeedback().size(), FEEDBACK.size());

    }
}
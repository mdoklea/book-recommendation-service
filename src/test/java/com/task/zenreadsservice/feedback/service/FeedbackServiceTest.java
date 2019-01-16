package com.task.zenreadsservice.feedback.service;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.books.service.BookService;
import com.task.zenreadsservice.feedback.model.Feedback;
import com.task.zenreadsservice.feedback.model.Rating;
import com.task.zenreadsservice.feedback.repository.FeedbackRepository;
import com.task.zenreadsservice.genres.model.Genre;
import com.task.zenreadsservice.users.model.GenrePreference;
import com.task.zenreadsservice.users.model.User;
import com.task.zenreadsservice.users.service.UserService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackServiceTest {

    private final static Genre GENRE_1 = Genre
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
            .genre(GENRE_1)
            .build();

    private final static User USER_DB = User
            .builder()
            .id(UUID.randomUUID())
            .username("name_surname_1")
            .firstName("Name")
            .lastName("Surname_1")
            .genrePreferences(new HashSet<>())
            .feedback(new HashSet<>())
            .build();

    private final static Set<Feedback> FEEDBACK = new HashSet<>(Arrays
            .asList(new Feedback(USER_DB, BOOK, Rating.LIKE)));

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    public void findAll_findAllSavedFeedback_returnsFeedbackList() {

        given(feedbackRepository.findAll()).willReturn(Collections.singletonList(new Feedback(USER_DB, BOOK, Rating.LIKE)));

        val actual =feedbackService.findAll();

        assertEquals(actual.size(), FEEDBACK.size());
        assertTrue(actual.stream().allMatch(feedback -> feedback.getUser().getUsername()
                .equals(USER_DB.getUsername())));
    }

    @Test
    public void saveAll_withFeedbackList_returnsNumberOfSavedFeedback() {

        val actual =feedbackService.saveAll(Collections.singletonList(new Feedback(USER_DB, BOOK, Rating.LIKE)));

        assertEquals(actual, FEEDBACK.size());
    }

    @Test
    public void save_withUserAndBookAndRating_returnsSavedFeedback() {
        given(feedbackRepository.save(any()) ).willReturn(new Feedback(USER_DB, BOOK, Rating.LIKE));

        val actual =feedbackService.save(USER_DB, BOOK, Rating.LIKE);

        assertTrue(actual.getUser().getUsername().equals(USER_DB.getUsername()));
    }

    @Test
    public void findAllByUser_withUser_returnsAllSavedFeedbackByGivenUser() {

        given(feedbackRepository.findAllByUser(any())).willReturn(Collections.singletonList(new Feedback(USER_DB, BOOK, Rating.LIKE)));

        val actual =feedbackService.findAllByUser(USER_DB);

        assertTrue(actual.stream().allMatch(feedback -> feedback.getUser().getUsername()
                .equals(USER_DB.getUsername())));

    }

    @Test
    public void findAllByUserAndRate_withUserAndRate_returnsAllSavedFeedbackByGivenUserAndRate() {

        given(feedbackService
                .findAllByUserAndRate(any(), any()))
                .willReturn(Collections.singletonList(new Feedback(USER_DB, BOOK, Rating.LIKE)));

        val actual =feedbackService.findAllByUserAndRate(USER_DB, Rating.LIKE);

        assertEquals(actual.size(), FEEDBACK.size());
        assertTrue(actual.stream().allMatch(feedback -> feedback.getUser().getUsername()
                .equals(USER_DB.getUsername())));
    }
}
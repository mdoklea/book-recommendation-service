package com.task.zenreadsservice.feedback.service;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.books.service.BookService;
import com.task.zenreadsservice.feedback.model.Rating;
import com.task.zenreadsservice.feedback.repository.FeedbackRepository;
import com.task.zenreadsservice.users.model.User;
import com.task.zenreadsservice.users.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final static int LIMIT_RESULTS = 5;
    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final BookService bookService;


    @Autowired
    public RecommendationService(FeedbackRepository feedbackRepository,
                                 UserService userService,
                                 BookService bookService){
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public List<Book> proposedBooksForFeedbackBasedOnUserGenrePreferences(final String username){

        val books = new ArrayList<Book>();

        val user = userService.findUserByUsername(username);

        if (!user.get().getGenrePreferences().isEmpty()){
            user.get().getGenrePreferences()
                    .stream()
                    .forEach(genrePreference -> {
                        val booksByGenre = bookService.findBooksByGenre(genrePreference.getGenre());
                        val excludedBooks = excludeBooksByUsersFeedback(user.get(), booksByGenre);
                        books.addAll(selectRandomBooksAndLimitResults(excludedBooks));
                    });
        }

        return books;
    }

    public List<Book> proposedBooksForFeedbackBasedOnOtherUserRates(final String username, final String rate){

        val user = userService.findUserByUsername(username);

        if( !feedbackRepository.findTopBooksRatedByOtherUsers(Rating.LIKE, user.get()).isEmpty()){
            val bookIds = feedbackRepository.findTopBooksRatedByOtherUsers(Rating.LIKE, user.get()).toArray();

            return updateSetOfBookIdsToBookSet(bookIds)
                    .stream()
                    .limit(LIMIT_RESULTS)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<Book> topFiveBooksByRate(final Rating rating){

        if( !feedbackRepository.findTopBooksByRate(rating).isEmpty()){
            val bookIds = feedbackRepository.findTopBooksByRate(rating).toArray();

            return updateSetOfBookIdsToBookSet(bookIds)
                    .stream()
                    .limit(LIMIT_RESULTS)
                    .collect(Collectors.toList());
        }

        return  new ArrayList<>();
    }

    private List<Book> updateSetOfBookIdsToBookSet(final Object[] bookIds){

        val books = new ArrayList<Book>();
        for (Object object : bookIds) {
             val id = object.toString();
             if(bookService.findBookById(UUID.fromString(id)).isPresent()){
                 val book = bookService.findBookById(UUID.fromString(id));
                 if(book.isPresent()) {
                     books.add(book.get());
                 }
             }
        }

        return new ArrayList<>(books);
    }

    private List<Book> excludeBooksByUsersFeedback (final User user, final List<Book> booksByGenre){

        val feedback = feedbackRepository.findAllByUser(user);

        return booksByGenre
                .stream()
                .filter(book -> !feedback.contains(book.getId()))
                .collect(Collectors.toList());
    }

    private List<Book> selectRandomBooksAndLimitResults(final List<Book> books){

        val booksRandom = new ArrayList<Book>();
        for(int i = 0; i < LIMIT_RESULTS; i++){
            Random rand = new Random();
            booksRandom.add(books.get(rand.nextInt(books.size())));
        }
        return booksRandom;
    }
}

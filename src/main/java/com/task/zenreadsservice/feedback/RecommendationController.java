package com.task.zenreadsservice.feedback;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.books.service.BookService;
import com.task.zenreadsservice.feedback.model.Rating;
import com.task.zenreadsservice.feedback.service.RecommendationService;
import com.task.zenreadsservice.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService, UserService userService, BookService bookService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/proposed-books/{username}")
    public ResponseEntity<List<Book>> getBooksByUserGenrePreferences(@PathVariable String username){

        if(!userService.findUserByUsername(username).isPresent()){
            return ResponseEntity.badRequest().build();
        }

        return  ResponseEntity.ok(recommendationService
                .proposedBooksForFeedbackBasedOnUserGenrePreferences(userService.findUserByUsername(username).get()));
    }

    @GetMapping("/proposed-books/{rating}/exclude/{username}")
    public ResponseEntity<List<Book>> getBooksBasedOnOtherUsersRating (@PathVariable String rating,
                                                                       @PathVariable String username){
        return  ResponseEntity.ok(recommendationService
                .proposedBooksForFeedbackBasedOnOtherUserRates(userService.findUserByUsername(username).get(),
                        Rating.valueOf(rating)));
    }

    @GetMapping("/top-books/{rating}")
    public ResponseEntity<List<Book>> topFiveBooksByRate (@PathVariable String rating){
        return  ResponseEntity.ok(recommendationService
                .topFiveBooksByRate(Rating.valueOf(rating)));
    }
}

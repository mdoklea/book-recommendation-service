package com.task.zenreadsservice.feedback;

import com.task.zenreadsservice.books.service.BookService;
import com.task.zenreadsservice.feedback.model.Feedback;
import com.task.zenreadsservice.feedback.model.Rating;
import com.task.zenreadsservice.feedback.service.FeedbackService;
import com.task.zenreadsservice.users.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, UserService userService, BookService bookService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllUsersFeedback(){
        return  ResponseEntity.ok(feedbackService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable String username ){

        if ( !Optional.of(userService.findUserByUsername(username)).isPresent() ){
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(feedbackService.findAllByUser(userService.findUserByUsername(username).get()));
    }

    @GetMapping("/{username}/{rating}")
    public ResponseEntity<List<Feedback>> getFeedbackByUserAndRate(@PathVariable String username,
                                                                   @PathVariable String rating  ){

        if ( !Optional.of(userService.findUserByUsername(username)).isPresent() ){
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(feedbackService
                .findAllByUserAndRate(userService.findUserByUsername(username).get(), Rating.valueOf(rating)));
    }

    @PostMapping
    public ResponseEntity<Object> saveFeedback(@RequestBody final Feedback feedback){

        if(!userService.findUserByUsername(feedback.getUser().getUsername()).isPresent() &&
                !bookService.findBookById(feedback.getBook().getId()).isPresent()) {
            return ResponseEntity.badRequest().body("Feedback failed to be saved!");
        }

        val user = userService.findUserByUsername(feedback.getUser().getUsername());
        val book = bookService.findBookById(feedback.getBook().getId());

        return  ResponseEntity.ok(feedbackService.save(user.get(), book.get(), feedback.getRate()));
    }

    @PostMapping("/add/all")
    public ResponseEntity<Object> saveFeedbackAll(@RequestBody final List<Feedback> feedback){

        if(feedback.isEmpty()){
            return ResponseEntity.badRequest().body("Feedback list is empty!");
        }
        val savedFeedback = feedback.stream().map(feedback1 -> {
            val user = userService.findUserByUsername(feedback1.getUser().getUsername());
            val book = bookService.findBookById(feedback1.getBook().getId());
            return new Feedback(user.get(), book.get(), feedback1.getRate());

        }).collect(Collectors.toList());

        return  ResponseEntity.ok(feedbackService.saveAll(savedFeedback) + " Feedback entries were saved." );
    }
}

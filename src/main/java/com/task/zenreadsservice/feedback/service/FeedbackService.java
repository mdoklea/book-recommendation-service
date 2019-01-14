package com.task.zenreadsservice.feedback.service;

import com.task.zenreadsservice.books.service.BookService;
import com.task.zenreadsservice.feedback.model.Feedback;
import com.task.zenreadsservice.feedback.model.Rating;
import com.task.zenreadsservice.feedback.repository.FeedbackRepository;
import com.task.zenreadsservice.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository,
                           UserService userService,
                           BookService bookService) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public List<Feedback> findAll(){
        return feedbackRepository.findAll();
    }

    public Feedback save(final Feedback feedback){

        if(userService.findUserById(feedback.getUser().getId()).isPresent() &&
                bookService.findBookById(feedback.getBook().getId()).isPresent()){

            return feedbackRepository.save(new Feedback(
                    userService.findUserById(feedback.getUser().getId()).get(),
                    bookService.findBookById(feedback.getBook().getId()).get(),
                    feedback.getRate()));
        }

        return null;
    }

    public List<Feedback> findAllByUsername(final String username){

        if(userService.findUserByUsername(username).isPresent()){
            return feedbackRepository.findAllByUser(
                    userService.findUserByUsername(username).get());
        }

        return new ArrayList<>();
    }
    public List<Feedback> findAllByUsernameAndRate(final String username, final String rating){

        if(userService.findUserByUsername(username).isPresent() && Rating.valueOf(rating) != null){
            return feedbackRepository.findAllByUserAndRate(
                    userService.findUserByUsername(username).get(),
                    Rating.valueOf(rating));
        }

        return new ArrayList<>();
    }

}

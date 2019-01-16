package com.task.zenreadsservice.feedback.service;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.feedback.model.Feedback;
import com.task.zenreadsservice.feedback.model.Rating;
import com.task.zenreadsservice.feedback.repository.FeedbackRepository;
import com.task.zenreadsservice.users.model.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> findAll(){
        return feedbackRepository.findAll();
    }

    public long saveAll(final List<Feedback> feedback) {

        val savedFeedback = feedback.stream().map(feedback1 -> {
            Feedback feedback2 = save(feedback1.getUser(), feedback1.getBook(), feedback1.getRate());
            return  feedback2;
        });


        return savedFeedback.count();
    }
    public Feedback save(final User user, final Book book, final Rating rating){
        return feedbackRepository.save(updateFeedback(user, book, rating));
    }

    public List<Feedback> findAllByUser(final User user){
        return feedbackRepository.findAllByUser(user);
    }

    public List<Feedback> findAllByUserAndRate(final User user, final Rating rating){
        return feedbackRepository.findAllByUserAndRate(user, rating);
    }

    private Feedback updateFeedback(final User user, final Book book, final Rating rating){

        Feedback feedback =  new Feedback(user, book, rating);
        if(feedbackRepository.findByUserAndBook(user,  book).isPresent()){
            feedback = feedbackRepository.findByUserAndBook(user,  book).get();
            feedback.setRate(rating);
        }

        return feedback;
    }

}

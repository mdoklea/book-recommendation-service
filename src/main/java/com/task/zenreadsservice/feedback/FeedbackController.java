package com.task.zenreadsservice.feedback;

import com.task.zenreadsservice.feedback.model.Feedback;
import com.task.zenreadsservice.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllUsersFeedback(){
        return  ResponseEntity.ok(feedbackService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable String username ){
          return  ResponseEntity.ok(feedbackService.findAllByUsername(username));
    }

    @GetMapping("/{username}/{rating}")
    public ResponseEntity<List<Feedback>> getFeedbackByUserAndRate(@PathVariable String username,
                                                                   @PathVariable String rating  ){
        return  ResponseEntity.ok(feedbackService
                .findAllByUsernameAndRate(username, rating));
    }

    @PostMapping
    public ResponseEntity<Object> saveFeedback(@RequestBody final Feedback feedback){

        if(feedbackService.save(feedback) == null){
            return ResponseEntity.badRequest().body("Feedback failed to be saved!");
        }
       return  ResponseEntity.ok(feedbackService.save(feedback));
    }


}

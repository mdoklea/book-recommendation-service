package com.task.zenreadsservice.feedback.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.users.model.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "feedback")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = { "user"})
@JsonIgnoreProperties(allowSetters = true, value = {"id"})
public class Feedback {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    private static class FeedbackId implements Serializable {

        @Column(name = "users_id")
        private UUID userId;

        @Column(name = "books_id")
        private UUID bookId;
    }

    @Id
    @Embedded
    private Feedback.FeedbackId id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("users_id")
    @JoinColumn(name = "users_id")
    @JsonIgnoreProperties(allowSetters = true, value = {"books"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("books_id")
    @JoinColumn(name = "books_id")
    @JsonIgnoreProperties(allowSetters = true, value = {"users"})
    private Book book;

    @Column(name = "rate")
    @Enumerated(EnumType.STRING)
    private Rating rate;

    public Feedback(User user, Book book, Rating rate) {
        this.user = user;
        this.book = book;
        this.rate = rate;
        this.id = new FeedbackId(user.getId(), book.getId());
    }
}

package com.task.zenreadsservice.books.model;

import com.task.zenreadsservice.genres.model.Genre;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "books")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"genre"})
@ToString(exclude ={"genre"})
public class Book {

    @Id
    @GeneratedValue
    private UUID id;

    private String isbn;
    private String fileName;
    private String imageUrlPath;
    private String author;
    private String title;

    @ManyToOne
    @JoinColumn(name = "genres_id", nullable = false)
    private Genre genre;

}

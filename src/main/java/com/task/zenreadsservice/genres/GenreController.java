package com.task.zenreadsservice.genres;

import com.task.zenreadsservice.genres.model.Genre;
import com.task.zenreadsservice.genres.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/genres")
public class GenreController {

    private  final GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres(){

        return ResponseEntity.ok(genreRepository.findAll());
    }
}

package com.task.zenreadsservice.genres.repository;

import com.task.zenreadsservice.genres.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    Optional<Genre> findGenreByCategory(String category);

}
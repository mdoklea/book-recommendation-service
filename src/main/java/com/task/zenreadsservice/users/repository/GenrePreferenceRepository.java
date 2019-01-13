package com.task.zenreadsservice.users.repository;

import com.task.zenreadsservice.users.model.GenrePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenrePreferenceRepository extends JpaRepository<GenrePreference, UUID> {
}
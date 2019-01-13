package com.task.zenreadsservice.users.repository;

import com.task.zenreadsservice.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUsersByUsername(final String username);
    Optional<User> findOneById(final UUID id);
}

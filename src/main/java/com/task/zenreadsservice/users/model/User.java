package com.task.zenreadsservice.users.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.task.zenreadsservice.feedback.model.Feedback;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "users")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString(exclude ={"genrePreferences, feedback"})
@EqualsAndHashCode(exclude = {"genrePreferences, feedback"})
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String firstName ;
    private String lastName;

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnoreProperties(allowSetters = true, value = {"user"})
    private Set<GenrePreference> genrePreferences = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnoreProperties(allowSetters = true, value = {"user"})
    private Set<Feedback> feedback = new HashSet<>();
}

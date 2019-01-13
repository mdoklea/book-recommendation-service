package com.task.zenreadsservice.users.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.task.zenreadsservice.genres.model.Genre;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "genre_preferences")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = { "user"})
@JsonIgnoreProperties(allowSetters = true, value = {"id"})
public class GenrePreference {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    private static class GenrePreferenceId implements Serializable {

        @Column(name = "users_id")
        private UUID userId;

        @Column(name = "genres_id")
        private UUID genreId;
    }

    @Id
    @Embedded
    private GenrePreferenceId id;

    @ManyToOne
    @MapsId("users_id")
    @JoinColumn(name = "users_id")
    @JsonIgnoreProperties(allowSetters = true, value = {"genres"})
    private User user;

    @ManyToOne
    @MapsId("genres_id")
    @JoinColumn(name = "genres_id")
    @JsonIgnoreProperties(allowSetters = true, value = {"users"})
    private Genre genre;

    public GenrePreference(final User user, final Genre genre) {
        this.id = new GenrePreferenceId(user.getId(), genre.getId());
        this.user = user;
        this.genre = genre;
    }
}

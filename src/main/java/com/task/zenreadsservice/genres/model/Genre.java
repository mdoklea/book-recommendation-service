package com.task.zenreadsservice.genres.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "genres")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue
    private UUID id;

    private String category;
}

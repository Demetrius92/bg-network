package com.analogicgames.bgnetwork.boardgame.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
public class BoardGame {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    @NonNull
    private String name;

    @NonNull
    private String description;

    private int minPlayers;

    private int maxPlayers;    

    @OneToMany
    @NonNull
    private List<Mechanism> mechanisms;

    @NonNull
    private Difficulty difficulty;

    private int duration;

    public BoardGame(@NonNull String name, @NonNull String description, int minPlayers, int maxPlayers,
            @NonNull List<Mechanism> mechanisms, @NonNull Difficulty difficulty, int duration) {
        this.name = name;
        this.description = description;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.mechanisms = mechanisms;
        this.difficulty = difficulty;
        this.duration = duration;
    }

}

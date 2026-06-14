package com.analogicgames.bgnetwork.boardgame;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BoardGame {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    private String name;

    private String description;

    private int minPlayers;

    private int maxPlayers;    

    @OneToMany
    private Set<Mechanism> mechanisms;

    private Difficulty difficulty;

    private int duration;

}

package com.analogicgames.bgnetwork.boardgame;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@DataJpaTest
public class BoardGameRepositoryTests {
    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void givenBoardGameRegistered_whenFindById_thenSuccess() {
        Mechanism newMechanism1 = new Mechanism("Dice Rolling", "Throw dice to get random results");
        Mechanism newMechanism2 = new Mechanism("Hand Management", "Players have a hand of cards they manage");
        testEntityManager.persist(newMechanism1);
        testEntityManager.persist(newMechanism2);
        List<Mechanism> mechanisms = List.of(newMechanism1, newMechanism2);
        BoardGame boardGame = new BoardGame("Catan", "A game in wich you have to trade very loud", 3, 4, mechanisms, Difficulty.BEGINNER, 60);
        testEntityManager.persist(boardGame);

        Optional<BoardGame> retrievedBoardGame = boardGameRepository.findById(boardGame.getEntityId());

        assertThat(retrievedBoardGame).contains(boardGame);
    }

    @Test
    public void givenTwoBoardGamesRegistered_whenFindAll_thenSuccess() {
        Mechanism newMechanism1 = new Mechanism("Dice Rolling", "Throw dice to get random results");
        Mechanism newMechanism2 = new Mechanism("Hand Management", "Players have a hand of cards they manage");
        Mechanism newMechanism3 = new Mechanism("Tile Placement", "Placing tiles on the table");
        Mechanism newMechanism4 = new Mechanism("Area Majority", "Have the majority of you game elements in a certain area");
        testEntityManager.persist(newMechanism1);
        testEntityManager.persist(newMechanism2);
        testEntityManager.persist(newMechanism3);
        testEntityManager.persist(newMechanism4);
        List<Mechanism> mechanismsCatan = List.of(newMechanism1, newMechanism2);
        List<Mechanism> mechanismsCarcassonne = List.of(newMechanism3, newMechanism4);
        BoardGame boardGame1 = new BoardGame("Catan", "A game in wich you have to trade very loud", 3, 4, mechanismsCatan, Difficulty.BEGINNER, 60);
        BoardGame boardGame2 = new BoardGame("Carcassone", "A game where you build roads and castles", 2, 5, mechanismsCarcassonne, Difficulty.BEGINNER, 45);
        testEntityManager.persist(boardGame1);
        testEntityManager.persist(boardGame2);
        
        List<BoardGame> boardGamesRetrieved = boardGameRepository.findAll();

        assertThat(boardGamesRetrieved.size()).isEqualTo(2);
        assertThat(boardGamesRetrieved).contains(boardGame1, boardGame2);
    }

    public void givenBoardGame_whenSave_thenSuccess() {
        Mechanism newMechanism1 = new Mechanism("Dice Rolling", "Throw dice to get random results");
        Mechanism newMechanism2 = new Mechanism("Hand Management", "Players have a hand of cards they manage");
        testEntityManager.persist(newMechanism1);
        testEntityManager.persist(newMechanism2);
        List<Mechanism> mechanismsCatan = List.of(newMechanism1, newMechanism2);
        BoardGame boardGame = new BoardGame("Catan", "A game in wich you have to trade very loud", 3, 4, mechanismsCatan, Difficulty.BEGINNER, 60);
        
        boardGameRepository.save(boardGame);

        BoardGame boardGameRetrieved = testEntityManager.find(BoardGame.class, boardGame.getEntityId());
        assertThat(boardGameRetrieved).isEqualTo(boardGame);
    }

    @Test
    public void givenBoardGameRegisterd_whenUpdate_thenSuccess() {
        Mechanism newMechanism1 = new Mechanism("Dice Rolling", "Throw dice to get random results");
        Mechanism newMechanism2 = new Mechanism("Hand Management", "Players have a hand of cards they manage");
        testEntityManager.persist(newMechanism1);
        testEntityManager.persist(newMechanism2);
        List<Mechanism> mechanisms = List.of(newMechanism1, newMechanism2);
        BoardGame boardGame = new BoardGame("Catan", "A game in wich you have to fight very loud", 3, 4, mechanisms, Difficulty.BEGINNER, 60);
        testEntityManager.persist(boardGame);
        
        boardGame.setDescription("A game in wich you have to trade very loud");
        boardGameRepository.save(boardGame);

        BoardGame retrievedBoardGame = testEntityManager.find(BoardGame.class, boardGame.getEntityId());
        assertThat(retrievedBoardGame).isEqualTo(boardGame);
    }
}

package com.analogicgames.bgnetwork.boardgame.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@DataJpaTest
public class BoardGameRepositoryIntegrationTests {
    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void givenBoardGameRegistered_whenFindById_thenSuccess() {
        BoardGame boardGame = createBoardGameCatan();
        boardGame.getMechanisms().forEach(testEntityManager::persist);
        testEntityManager.persist(boardGame);

        Optional<BoardGame> retrievedBoardGame = boardGameRepository.findById(boardGame.getEntityId());

        assertThat(retrievedBoardGame).contains(boardGame);
    }

    @Test
    public void givenTwoBoardGamesRegistered_whenFindAll_thenSuccess() {
        BoardGame boardGame1 = createBoardGameCatan();
        BoardGame boardGame2 = createBoardGameCarcassonne();
        boardGame1.getMechanisms().forEach(testEntityManager::persist);
        boardGame2.getMechanisms().forEach(testEntityManager::persist);
        testEntityManager.persist(boardGame1);
        testEntityManager.persist(boardGame2);
        
        List<BoardGame> boardGamesRetrieved = boardGameRepository.findAll();

        assertThat(boardGamesRetrieved.size()).isEqualTo(2);
        assertThat(boardGamesRetrieved).contains(boardGame1, boardGame2);
    }

    @Test
    public void givenBoardGame_whenSave_thenSuccess() {
        BoardGame boardGame = createBoardGameCatan();
        boardGame.getMechanisms().forEach(testEntityManager::persist);
        
        boardGameRepository.save(boardGame);

        BoardGame boardGameRetrieved = testEntityManager.find(BoardGame.class, boardGame.getEntityId());
        assertThat(boardGameRetrieved).isEqualTo(boardGame);
    }

    @Test
    public void givenBoardGameRegistered_whenUpdate_thenSuccess() {
        BoardGame boardGame = createBoardGameCatan();
        boardGame.getMechanisms().forEach(testEntityManager::persist);
        testEntityManager.persist(boardGame);
        
        boardGame.setDescription("A game in wich you have to fight very loud");
        boardGameRepository.save(boardGame);

        BoardGame retrievedBoardGame = testEntityManager.find(BoardGame.class, boardGame.getEntityId());
        assertThat(retrievedBoardGame).isEqualTo(boardGame);
    }

    private BoardGame createBoardGameCatan() {
        Mechanism mechanismDiceRolling = new Mechanism("Dice Rolling", "Throw dice to get random results");
        Mechanism mechanismHandManagement = new Mechanism("Hand Management", "Players have a hand of cards they manage");
        List<Mechanism> mechanisms = List.of(mechanismDiceRolling, mechanismHandManagement);
        BoardGame boardGameCatan = new BoardGame("Catan", "A game in wich you have to trade very loud", 3, 4, mechanisms, Difficulty.BEGINNER, 60);
        
        return boardGameCatan;
    }

    private BoardGame createBoardGameCarcassonne() {
        Mechanism mechanismTilePlacement = new Mechanism("Tile Placement", "Placing tiles on the table");
        Mechanism mechanismAreaMajority = new Mechanism("Area Majority", "Have the majority of you game elements in a certain area");
        List<Mechanism> mechanismsCarcassonne = List.of(mechanismTilePlacement, mechanismAreaMajority);                
        BoardGame boardGameCarcassonne = new BoardGame("Carcassone", "A game where you build roads and castles", 2, 5, mechanismsCarcassonne, Difficulty.BEGINNER, 45);

        return boardGameCarcassonne;
    }
}

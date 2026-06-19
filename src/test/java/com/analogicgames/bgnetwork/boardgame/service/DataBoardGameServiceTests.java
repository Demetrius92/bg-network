package com.analogicgames.bgnetwork.boardgame.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.analogicgames.bgnetwork.boardgame.model.BoardGame;
import com.analogicgames.bgnetwork.boardgame.model.BoardGameRepository;
import com.analogicgames.bgnetwork.boardgame.model.Difficulty;
import com.analogicgames.bgnetwork.boardgame.model.Mechanism;

@ExtendWith(MockitoExtension.class)
public class DataBoardGameServiceTests {
    @Mock
    private BoardGameRepository boardGameRepository;

    @InjectMocks
    private DataBoardGameService dataBoardGameService;

    @Test
    public void givenTwoBoardGamesRegistered_whenGetAllBoardGames_thenSuccess() {
        BoardGame boardGame1 = createBoardGameCatan();
        BoardGame boardGame2 = createBoardGameCarcassonne();
        when(boardGameRepository.findAll()).thenReturn(List.of(
            boardGame1,
            boardGame2
        ));

        List<BoardGame> retrievedBoardGames = dataBoardGameService.getAllBoardGames();

        assertThat(retrievedBoardGames.size()).isEqualTo(2);
        assertThat(retrievedBoardGames).contains(boardGame1);
        assertThat(retrievedBoardGames).contains(boardGame2);
        verify(boardGameRepository).findAll();
    }

    @Test
    public void givenBoardGameRegistered_whenGetBoardGame_thenSuccess() throws BoardGameNotFoundException {
        BoardGame boardGameWithId = createBoardGameCatan();
        boardGameWithId.setEntityId(1L);
        when(boardGameRepository.findById(boardGameWithId.getEntityId()))
            .thenReturn(Optional.of(boardGameWithId));

        BoardGame retrievedBoardGame = dataBoardGameService.getBoardGame(boardGameWithId.getEntityId());

        assertThat(retrievedBoardGame).isEqualTo(boardGameWithId);
        verify(boardGameRepository).findById(boardGameWithId.getEntityId());
    }

    @Test
    public void giveNewBoardGame_whenRegisterBoardGame_thenSuccess() {
        BoardGame boardGame = createBoardGameCatan();
        BoardGame boardGameWithId = createBoardGameCatan();
        boardGameWithId.setEntityId(1L);
        when(boardGameRepository.save(boardGame)).thenReturn(boardGameWithId);

        BoardGame registeredBoardGame = dataBoardGameService.registerBoardGame(boardGame);

        assertThat(registeredBoardGame).isEqualTo(boardGameWithId);
        verify(boardGameRepository).save(boardGame);
    }

    @Test
    public void givenUpdatedMechanism_whenUpdateMechanism_thenSuccess() throws BoardGameNotFoundException {
        BoardGame boardGameWithId = createBoardGameCatan();
        boardGameWithId.setEntityId(1L);
        when(boardGameRepository.existsById(boardGameWithId.getEntityId()))
        .thenReturn(true);

        dataBoardGameService.updateBoardGame(boardGameWithId);

        verify(boardGameRepository).save(boardGameWithId);
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

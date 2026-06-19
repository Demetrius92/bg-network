package com.analogicgames.bgnetwork.boardgame.service;

import java.util.List;

import com.analogicgames.bgnetwork.boardgame.model.BoardGame;

public interface BoardGameService {

    List<BoardGame> getAllBoardGames();

    BoardGame getBoardGame(Long id) throws BoardGameNotFoundException;

    BoardGame registerBoardGame(BoardGame boardGame);

    void updateBoardGame(BoardGame boardGame) throws BoardGameNotFoundException;
}

package com.analogicgames.bgnetwork.boardgame;

import java.util.List;

public interface BoardGameService {

    List<BoardGame> getAllBoardGames();

    BoardGame getBoardGame(Long id) throws BoardGameNotFoundException;

    BoardGame registerBoardGame(BoardGame boardGame);

    void updateBoardGame(BoardGame boardGame) throws BoardGameNotFoundException;
}

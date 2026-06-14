package com.analogicgames.bgnetwork.boardgame;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataBoardGameService implements BoardGameService {
    @Autowired
    BoardGameRepository boardGameRepository;

    @Override
    public List<BoardGame> getAllBoardGames() {
        return boardGameRepository.findAll();
    }

    @Override
    public BoardGame getBoardGame(Long id) throws BoardGameNotFoundException {
        return boardGameRepository
            .findById(id)
            .orElseThrow(() -> new BoardGameNotFoundException("Board Game not found"));
    }

    @Override
    public BoardGame registerBoardGame(BoardGame boardGame) {
        return boardGameRepository.save(boardGame);
    }

    @Override
    public void updateBoardGame(BoardGame boardGame) throws BoardGameNotFoundException {
        if (!boardGameRepository.existsById(boardGame.getEntityId()))
            throw new BoardGameNotFoundException("Board Game not found");
        boardGameRepository.save(boardGame);
    }

}

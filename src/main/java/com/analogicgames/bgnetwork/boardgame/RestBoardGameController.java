package com.analogicgames.bgnetwork.boardgame;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestBoardGameController {
    @Autowired
    private BoardGameService boardGameService;

    @Autowired
    private BoardGameMapper boardGameMapper;

    @GetMapping("/boardgames")
    public List<BoardGameDto> listBoardgames() {
        return boardGameService
            .getAllBoardGames()
            .parallelStream()
            .map(boardGameMapper::boardGameToDto)
            .toList();
    }

    @GetMapping("/boardgames/{id}")
    public BoardGameDto getBoardGame(@PathVariable Long id) throws BoardGameNotFoundException {
        return boardGameMapper
            .boardGameToDto(
                boardGameService.getBoardGame(id)
            );
    }

    @PostMapping("/boardgames")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BoardGameDto registerBoardGame(@RequestBody BoardGameDto boardGameDto) {
        return boardGameMapper
            .boardGameToDto(
                boardGameService.registerBoardGame(
                    boardGameMapper.dtoToBoardGame(boardGameDto)
                )
            );
    }

    @PutMapping("/boardgames/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateBoardGame(@PathVariable Long id, @RequestBody BoardGameDto boardGameDto) throws BoardGameNotFoundException {
        BoardGame boardGame = boardGameService.getBoardGame(id);
        BoardGame updatedBoardGame = boardGameMapper.updateBoardGameFromDto(boardGameDto, boardGame);
        boardGameService.updateBoardGame(updatedBoardGame);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(exception = { BoardGameNotFoundException.class })
    public void handleNotFound() {
        
    }
}

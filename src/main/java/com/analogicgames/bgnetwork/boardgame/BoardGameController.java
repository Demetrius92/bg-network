package com.analogicgames.bgnetwork.boardgame;

import java.util.List;
import java.util.stream.StreamSupport;

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
public class BoardGameController {
    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private BoardGameMapper boardGameMapper;

    @GetMapping("/boardgames")
    public List<BoardGameDto> listBoardgames() {
        return StreamSupport
            .stream(boardGameRepository.findAll().spliterator(), true)
            .map(boardGame -> boardGameMapper.boardGameToDto(boardGame))
            .toList();
    }

    @GetMapping("/boardgames/{id}")
    public BoardGameDto getBoardGame(@PathVariable Long id) throws BoardGameNotFoundException {
        return boardGameMapper.boardGameToDto(
            boardGameRepository
                .findById(id)
                .orElseThrow(() -> new BoardGameNotFoundException("Board Game Not Found"))
        );
    }

    @PostMapping("/boardgames")
    public BoardGameDto registerBoardGame(@RequestBody BoardGameDto boardGameDto) {
        return boardGameMapper
            .boardGameToDto(
                boardGameRepository.save(boardGameMapper.dtoToBoardGame(boardGameDto)
            )
        );
    }

    @PutMapping("/boardgames/{id}")
    public void updateBoardGame(@PathVariable Long id, @RequestBody BoardGameDto boardGameDto) throws BoardGameNotFoundException {
        BoardGame boardGame = boardGameRepository.findById(id).orElseThrow(() -> new BoardGameNotFoundException("Board Game Not Found"));
        boardGameMapper.updateBoardGameFromDto(boardGameDto, boardGame);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(exception = { BoardGameNotFoundException.class })
    public void handleNotFound() {
        
    }
}

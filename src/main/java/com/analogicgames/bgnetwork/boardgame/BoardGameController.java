package com.analogicgames.bgnetwork.boardgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardGameController {
    @Autowired
    private BoardGameRepository boardGameRepository;

    @GetMapping("/boardgames")
    public Iterable<BoardGame> listBoardgames() {
        return boardGameRepository.findAll();
    }

    @GetMapping("/boardgames/{id}")
    public BoardGame getBoardGame(@PathVariable Long id) throws BoardGameNotFoundException {
        return boardGameRepository.findById(id).orElseThrow(() -> new BoardGameNotFoundException("Board Game Not Found"));
    }

    @PostMapping("/boardgames")
    public BoardGame registerBoardGame(@RequestBody BoardGame boardGame) {
        return boardGameRepository.save(boardGame);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(exception = { BoardGameNotFoundException.class })
    public void handleNotFound() {
        
    }
}

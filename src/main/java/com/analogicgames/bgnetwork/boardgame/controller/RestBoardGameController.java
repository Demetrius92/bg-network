package com.analogicgames.bgnetwork.boardgame.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.analogicgames.bgnetwork.boardgame.model.BoardGame;
import com.analogicgames.bgnetwork.boardgame.model.BoardGameDto;
import com.analogicgames.bgnetwork.boardgame.model.BoardGameMapper;
import com.analogicgames.bgnetwork.boardgame.service.BoardGameNotFoundException;
import com.analogicgames.bgnetwork.boardgame.service.BoardGameService;

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
    public ResponseEntity<BoardGameDto> registerBoardGame(@RequestBody BoardGameDto boardGameDto) {
        BoardGameDto boardGameDtoResponse = 
            boardGameMapper
                .boardGameToDto(
                    boardGameService.registerBoardGame(
                        boardGameMapper.dtoToBoardGame(boardGameDto)
                    )
                );

        return ResponseEntity
            .created(getLocation(boardGameDtoResponse.getEntityId()))
            .body(boardGameDtoResponse);
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

    private URI getLocation(Long resourceId) {
        return ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .path("/{resourceId}")
            .buildAndExpand(resourceId)
            .toUri();
    } 
}

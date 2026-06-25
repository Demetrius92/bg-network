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

import com.analogicgames.bgnetwork.boardgame.model.Mechanism;
import com.analogicgames.bgnetwork.boardgame.model.MechanismDto;
import com.analogicgames.bgnetwork.boardgame.model.MechanismMapper;
import com.analogicgames.bgnetwork.boardgame.service.MechanismNotFoundException;
import com.analogicgames.bgnetwork.boardgame.service.MechanismService;

@RestController
public class RestMechanismController {
    @Autowired
    private MechanismService mechanismService;

    @Autowired
    private MechanismMapper mechanismMapper;

    @GetMapping("/mechanisms")
    public List<MechanismDto> listMechanisms() {
        return mechanismService
            .getAllMechanims()
            .parallelStream()
            .map(mechanismMapper::mechanismToDto)
            .toList();
    }

    @GetMapping("/mechanisms/{id}")
    public MechanismDto getMechanism(@PathVariable Long id) throws MechanismNotFoundException {
        return mechanismMapper
            .mechanismToDto(
                mechanismService.getMechanism(id)
            );
    }

    @PostMapping("/mechanisms")
    public ResponseEntity<MechanismDto> registerMechanism(@RequestBody MechanismDto mechanismDto) {
        MechanismDto mechanismDtoResponse = 
            mechanismMapper
                .mechanismToDto(
                    mechanismService.registerMechanism(
                        mechanismMapper.dtoToMechanism(mechanismDto)
                    )
                );
        
        return ResponseEntity.created(getLocation(mechanismDtoResponse.getEntityId())).body(mechanismDtoResponse);
    }

    @PutMapping("/mechanisms/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateMechanism(@PathVariable Long id, @RequestBody MechanismDto mechanismDto) throws MechanismNotFoundException {
        Mechanism mechanism = mechanismService.getMechanism(id);
        Mechanism updatedMechanism = mechanismMapper.updateMechanismFromDto(mechanismDto, mechanism);
        mechanismService.updateMechanism(updatedMechanism);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(exception = { MechanismNotFoundException.class })
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

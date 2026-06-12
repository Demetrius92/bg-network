package com.analogicgames.bgnetwork.boardgame;

import java.util.List;
import java.util.stream.Collectors;
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
public class MechanismController {
    @Autowired
    private MechanismRepository mechanismRepository;

    @Autowired
    private MechanismMapper mechanismMapper;

    @GetMapping("/mechanisms")
    public List<MechanismDto> listMechanisms() {
        return StreamSupport
            .stream(mechanismRepository.findAll().spliterator(), true)
            .map(mechanism -> mechanismMapper.mechanismToDto(mechanism))
            .toList();
    }

    @GetMapping("/mechanisms/{id}")
    public MechanismDto getMechanism(@PathVariable Long id) throws MechanismNotFoundException {
        return mechanismMapper.mechanismToDto(
            mechanismRepository
                .findById(id)
                .orElseThrow(() -> new MechanismNotFoundException("Mechanism not found")));
    }

    @PostMapping("/mechanisms")
    public MechanismDto registerMechanism(@RequestBody MechanismDto mechanismDto) {
        return mechanismMapper
            .mechanismToDto(
                mechanismRepository.save(mechanismMapper.dtoToMechanism(mechanismDto)
            )
        );
    }

    @PutMapping("/mechanisms/{id}")
    public void updateMechanism(@PathVariable Long id, @RequestBody MechanismDto mechanismDto) throws MechanismNotFoundException {
        Mechanism mechanism = mechanismRepository.findById(id).orElseThrow(() -> new MechanismNotFoundException("Mechanism not found"));
        mechanismMapper.updateMechanismFromDto(mechanismDto, mechanism);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(exception = { MechanismNotFoundException.class })
    public void handleNotFound() {
        
    }    
}

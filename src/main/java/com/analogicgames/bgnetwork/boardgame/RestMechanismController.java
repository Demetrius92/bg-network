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
    @ResponseStatus(code = HttpStatus.CREATED)
    public MechanismDto registerMechanism(@RequestBody MechanismDto mechanismDto) {
        return mechanismMapper
            .mechanismToDto(
                mechanismService.registerMechanism(
                    mechanismMapper.dtoToMechanism(mechanismDto)
                )
            );
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
}

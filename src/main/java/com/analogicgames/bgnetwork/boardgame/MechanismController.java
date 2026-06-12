package com.analogicgames.bgnetwork.boardgame;

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

    @GetMapping("/mechanisms")
    public Iterable<Mechanism> listMechanisms() {
        return mechanismRepository.findAll();
    }

    @GetMapping("/mechanisms/{id}")
    public Mechanism getMechanism(@PathVariable Long id) throws MechanismNotFoundException {
        return mechanismRepository.findById(id).orElseThrow(() -> new MechanismNotFoundException("Mechanism not found"));
    }

    @PostMapping("/mechanisms")
    public Mechanism registerMechanism(@RequestBody Mechanism mechanism) {
        return mechanismRepository.save(mechanism);
    }

    @PutMapping("/mechanisms/{id}")
    public Mechanism updateMechanism(@PathVariable Long id, @RequestBody Mechanism mechanism) {
        return mechanismRepository.save(mechanism);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(exception = { MechanismNotFoundException.class })
    public void handleNotFound() {
        
    }    
}

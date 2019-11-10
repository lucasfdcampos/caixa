package br.com.caixa.controller;

import br.com.caixa.model.Atm;
import br.com.caixa.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/atm")
public class AtmController {

    @Autowired
    private AtmService atmService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addAtm(@RequestBody Atm atm) {
        try {
            this.atmService.save(atm);
            return new ResponseEntity<String>(atm.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateAtm(@PathVariable("id") Long id, @RequestBody Atm atm) {
        try {
            this.atmService.save(atm);
            return new ResponseEntity<String>(atm.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getAtm(@PathVariable("id") Long id) {
        try {
            Atm atm = null;
            atm = this.atmService.findById(id);
            return new ResponseEntity<String>(atm.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}

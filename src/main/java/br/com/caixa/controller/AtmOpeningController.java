package br.com.caixa.controller;

import br.com.caixa.model.Atm;
import br.com.caixa.model.AtmOpening;
import br.com.caixa.model.Money;
import br.com.caixa.service.AtmOpeningService;
import br.com.caixa.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/atmopening")
public class AtmOpeningController {

    @Autowired
    private AtmOpeningService atmOpeningService;
    private AtmService atmService;

    @PutMapping(value = "/{id}/open")
    public ResponseEntity<String> openAtm(@PathVariable Long id, @RequestBody Money money) {
        Atm atm = this.atmService.findById(id);
        this.atmOpeningService.openAtm(atm, money);
        return new ResponseEntity<String>(atm.toString(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/close")
    public ResponseEntity<String> closeAtm(@PathVariable Long id) {
        Atm atm = this.atmService.findById(id);
        this.atmOpeningService.closeAtm(atm);
        return new ResponseEntity<String>(atm.toString(), HttpStatus.OK);
    }
}

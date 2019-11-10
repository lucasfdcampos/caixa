package br.com.caixa.controller;

import br.com.caixa.model.Atm;
import br.com.caixa.model.AtmOpening;
import br.com.caixa.model.Money;
import br.com.caixa.repository.AtmOpeningRepository;
import br.com.caixa.service.AtmOpeningService;
import br.com.caixa.service.AtmService;
import br.com.caixa.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path="/api/atmopening")
public class AtmOpeningController {

    @Autowired
    private AtmOpeningService atmOpeningService;

    @Autowired
    private AtmService atmService;

    @Autowired
    private MoneyService moneyService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getOpeningService(@PathVariable("id") Long id) {
        try {
            AtmOpening atmOpening = null;
            atmOpening = this.atmOpeningService.findById(id);
            return new ResponseEntity<String>(atmOpening.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}/open")
    public ResponseEntity<String> openAtm(@PathVariable("id") Long id, @RequestBody Money money) {
        try {
            Atm atm = null;
            atm = this.atmService.findById(id);
            String opening = this.atmOpeningService.openAtm(atm, money);
            return new ResponseEntity<String>(opening, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}/close")
    public ResponseEntity<String> closeAtm(@PathVariable("id") Long id) {
        try {
            Atm atm = null;
            atm = this.atmService.findById(id);
            String closing = this.atmOpeningService.closeAtm(atm);
            return new ResponseEntity<String>(closing, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }

    }
}

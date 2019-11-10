package br.com.caixa.controller;

import br.com.caixa.model.AtmMovement;
import br.com.caixa.service.AtmMovementService;
import br.com.caixa.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/atmmovement")
public class AtmMovementController {

    @Autowired
    private AtmMovementService atmMovementService;

    @PostMapping(value = "/movement")
    public ResponseEntity<String> movement(@RequestBody AtmMovement atmMovement) {
        try {
            AtmMovement atmMovemented = null;
            atmMovemented = this.atmMovementService.save(atmMovement);
            return new ResponseEntity<String>(atmMovement.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}

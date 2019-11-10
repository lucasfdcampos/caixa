package br.com.caixa.controller;

import br.com.caixa.model.Account;
import br.com.caixa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        try {
            this.accountService.save(account);
            return new ResponseEntity<String>(account.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getAccount(@PathVariable("id") Long id) {
        try {
            Account account = null;
            account = this.accountService.findById(id);
            return new ResponseEntity<String>(account.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}/balance")
    public ResponseEntity<String> getAccountBalance(@PathVariable("id") Long id) {
        try {
            Double accountBalance = this.accountService.findAccountBalance(id);
            return new ResponseEntity<String>("Balance: " + accountBalance.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{agency}/{number}/balance")
    public ResponseEntity<String> getAccountBalance(@PathVariable("agency") String agency,
                                                    @PathVariable("number") String number) {
        try {
            Double accountBalance = this.accountService.findAccountBalance(agency, number);
            return new ResponseEntity<String>("Balance: " + accountBalance.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}

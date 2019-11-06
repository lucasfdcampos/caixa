package br.com.caixa.service;

import br.com.caixa.model.AccountMovement;
import br.com.caixa.repository.AccountMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountMovementService {

    @Autowired
    private AccountMovementRepository accountMovementRepository;

    public AccountMovementService() {
    }

    public AccountMovementService(AccountMovementRepository accountMovementRepository) {
        super();
        this.accountMovementRepository = accountMovementRepository;
    }

    public void save(AccountMovement accountMovement) {
        this.accountMovementRepository.save(accountMovement);
    }
}

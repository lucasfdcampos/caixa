package br.com.caixa.service;

import br.com.caixa.model.Account;
import br.com.caixa.model.AccountMovement;
import br.com.caixa.repository.AccountMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountMovementService {
    private static final String NOT_FOUND = "Account Movement not found";
    private static final String EMPTY = "Account Movement is empty";

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

    public List<AccountMovement> findAllByAccount(Account account) {

        List<AccountMovement> allByAccount = this.accountMovementRepository.findAllByAccount(account.getId());

        if (allByAccount == null) {
            throw new ServiceException(NOT_FOUND);

        } else if (allByAccount.isEmpty()) {
            throw new ServiceException(EMPTY);
        }
        return allByAccount;
    }
}

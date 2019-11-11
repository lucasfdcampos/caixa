package br.com.caixa.service;


import br.com.caixa.model.Account;
import br.com.caixa.model.AccountMovement;
import br.com.caixa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private static final String ACCOUNT_NULL = "Object Account is null";
    private static final String ACCOUNT_INVALID = "Account invalid";
    private static final String AGENCY_EMPTY = "Agency is empty";
    private static final String NUMBER_EMPTY = "Account number is empty";
    private static final String BALANCE_INVALID = "Balance value invalid";
    private static final String ID_INVALID = "Account ID invalid";
    private static final String NOT_FOUND = "Account not found";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMovementService accountMovementService;

    public AccountService() {
    }

    public AccountService(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }

    public void save(Account account) {

        if (account == null) {
            throw new ServiceException(ACCOUNT_NULL);

        } else if ((account.getAgency()) == null || (account.getNumber()) == null) {
            throw new ServiceException(ACCOUNT_INVALID);

        } else if (account.getAgency().isEmpty()) {
            throw new ServiceException(AGENCY_EMPTY);

        } else if (account.getNumber().isEmpty()) {
            throw new ServiceException(NUMBER_EMPTY);

        } else if (account.getBalance() < 0) {
            throw new ServiceException(BALANCE_INVALID);
        }
        this.accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {

        if ((id == null) || (id <= 0)) {
            throw new ServiceException(ID_INVALID);
        }

        Account account = null;
        try {
            account = this.accountRepository.getOne(id);

        } catch (EntityNotFoundException e) {
            throw new ServiceException(NOT_FOUND);

        } catch (ServiceException e) {
            throw new ServiceException(NOT_FOUND);
        }
        return account;
    }

    @Transactional(readOnly = true)
    public Account findByAgencyNumber(String agency, String number) {

        if (agency == null || number == null) {
            throw new ServiceException(ACCOUNT_INVALID);

        } else if (agency.isEmpty()) {
            throw new ServiceException(AGENCY_EMPTY);

        } else if (number.isEmpty()) {
            throw new ServiceException(NUMBER_EMPTY);
        }

        Account account = null;
        try {
            account = this.accountRepository.findByAgencyNumber(agency, number);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(NOT_FOUND);

        } catch (ServiceException e) {
            throw new ServiceException(NOT_FOUND);
        }
        return account;
    }

    @Transactional(readOnly = true)
    public Double findAccountBalance(Long id) {
        Account account = findById(id);
        Double balance = account.getBalance();
        return balance;
    }

    @Transactional(readOnly = true)
    public Double findAccountBalance(String agency, String number) {

        if (agency == null || number == null) {
            throw new ServiceException(ACCOUNT_INVALID);

        } else if (agency.isEmpty()) {
            throw new ServiceException(AGENCY_EMPTY);

        } else if (number.isEmpty()) {
            throw new ServiceException(NUMBER_EMPTY);
        }

        Account account = null;
        try {
            account = this.findByAgencyNumber(agency, number);

        } catch (EntityNotFoundException e) {
            throw new ServiceException(NOT_FOUND);

        } catch (ServiceException e) {
            throw new ServiceException(NOT_FOUND);
        }
        Double balance = account.getBalance();
        return balance;
    }

    @Transactional(readOnly = true)
    public List<AccountMovement> bankStatement(String agency, String number) {

        Account account = null;
        try {
            account = this.findByAgencyNumber(agency, number);

        } catch (EntityNotFoundException e) {
            throw new ServiceException(NOT_FOUND);

        } catch (ServiceException e) {
            throw new ServiceException(NOT_FOUND);
        }
        List<AccountMovement> bankStatement = this.accountMovementService.findAllByAccount(account);
        return bankStatement;
    }
}

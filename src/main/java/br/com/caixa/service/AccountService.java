package br.com.caixa.service;


import br.com.caixa.model.Account;
import br.com.caixa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountService() {
    }

    public AccountService(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }

    public void save(Account account) {
        this.accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return this.accountRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public Account findByAgencyNumber(String agency, String number) {
        return this.accountRepository.findByAgencyNumber(agency, number);
    }

    @Transactional(readOnly = true)
    public Double findAccountBalance(Long id) {
        Account account = findById(id);
        Double balance = account.getBalance();
        return balance;
    }

    @Transactional(readOnly = true)
    public Double findAccountBalance(String agency, String number) {
        Account account = findByAgencyNumber(agency, number);
        Double balance = account.getBalance();
        return balance;
    }
}

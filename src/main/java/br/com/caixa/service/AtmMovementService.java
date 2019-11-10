package br.com.caixa.service;

import br.com.caixa.model.*;
import br.com.caixa.repository.AtmMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class AtmMovementService {
    private static final String ATM_MOVEMENT_NULL = "Object AtmMovement is null";
    private static final String ATM_NULL = "ATM is null";
    private static final String ATM_INVALID = "ATM invalid";
    private static final String ATM_CLOSED = "ATM closed";
    private static final String ATM_NOT_OPENED = "ATM not opened";
    private static final String ATM_OPENING_INACTIVE = "ATM not active";
    private static final String ACCOUNT_NULL = "Account is null";
    private static final String ACCOUNT_INVALID = "Account invalid";
    private static final String NOT_BALANCE = "Insufficient funds";
    private static final String WITHOUT_BANKNOTES = "ATM without banknotes";
    private static final String VALUE_INVALID = "Invalid value to withdraw";
    private static final String ACCOUNT_DESTINY_NULL = "Account destiny is null";
    private static final String ACCOUNT_DESTINY_INVALID = "Account destiny invalid";
    private static final String INVALID_OPERATION = "Invalid operation";

    @Autowired
    private AtmMovementRepository atmMovementRepository;

    @Autowired
    private AtmService atmService;

    @Autowired
    private AtmOpeningService atmOpeningService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMovementService accountMovementService;

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private WithdrawService withdrawService;

    public AtmMovementService() {
    }

    public AtmMovement save(AtmMovement atmMovement) {

        if (atmMovement == null) {
            throw new ServiceException(ATM_MOVEMENT_NULL);
        }

        // Verifica o caixa ATM
        if (atmMovement.getAtm() == null) {
            throw new ServiceException(ATM_NULL);

        } else if ((atmMovement.getAtm().getId() == null) || (atmMovement.getAtm().getId() <= 0)) {
            throw new ServiceException(ATM_INVALID);
        }

        Atm atm = this.atmService.findById(atmMovement.getAtm().getId());

        if (atm == null) {
            throw new ServiceException(ATM_INVALID);
        }

        // Status ATM
        if (!atm.getActive()) {
            throw new ServiceException(ATM_CLOSED);
        }

        // Verifica o ATM Opening
        AtmOpening atmOpening = atmOpeningService.findByActiveAndAtm(true, atm);

        if (atmOpening == null) {
            throw new ServiceException(ATM_NOT_OPENED);

        } else if (!atmOpening.getActive()) {
            throw new ServiceException(ATM_OPENING_INACTIVE);
        }

        // Verifica a conta
        if (atmMovement.getAccount() == null) {
            throw new ServiceException(ACCOUNT_NULL);

        } else if ((atmMovement.getAccount().getAgency() == null) || (atmMovement.getAccount().getNumber() == null)) {
            throw new ServiceException(ACCOUNT_INVALID);
        }

        Account account = accountService.findByAgencyNumber(atmMovement.getAccount().getAgency(),
                atmMovement.getAccount().getNumber());

        if (account == null) {
            throw new ServiceException(ACCOUNT_INVALID);
        }

        // Conta destino (transferencia)
        Account accountDestiny = null;

        // Validacoes para SAQUE e TRANSFERENCIA
        if ((atmMovement.getMovementType().equals(MovementType.SAQUE)) ||
                (atmMovement.getMovementType().equals(MovementType.TRANSFERENCIA))) {

            // Verifica o saldo
            if (account.getBalance() < atmMovement.getValue()) {
                throw new ServiceException(NOT_BALANCE);
            }

            // Validacoes para SAQUE
            if (atmMovement.getMovementType().equals(MovementType.SAQUE)) {

                // Verifica se o ATM opening contem dinheiro
                if ((atmOpening.getBegin() == null) && (atmOpening.getCurrent() == null)) {
                    throw new ServiceException(WITHOUT_BANKNOTES);
                }

                // Verifica se o valor requisitado para SAQUE e menor que 5 ou mod(5) diferente de zero
                if ((atmMovement.getValue() < 5d) || ((atmMovement.getValue() % 5d) != 0)) {
                    throw new ServiceException(VALUE_INVALID);
                }
            }

            // Validacoes conta destino para TRANSFERENCIA
            if (atmMovement.getMovementType().equals(MovementType.TRANSFERENCIA)) {
                // Conta destino
                if (atmMovement.getAccountDestiny() == null) {
                    throw new ServiceException(ACCOUNT_DESTINY_NULL);

                } else if ((atmMovement.getAccountDestiny().getAgency() == null) ||
                        (atmMovement.getAccountDestiny().getNumber() == null)) {
                    throw new ServiceException(ACCOUNT_DESTINY_INVALID);
                }

                accountDestiny = accountService.findByAgencyNumber(atmMovement.getAccountDestiny().getAgency(),
                        atmMovement.getAccountDestiny().getNumber());

                if (accountDestiny == null) {
                    throw new ServiceException(ACCOUNT_DESTINY_INVALID);
                }
            }
        }

        // ATM Movement
        atmMovement.setAtm(atm);
        atmMovement.setDateTransaction(new Date());
        atmMovement.setAccount(account);
        atmMovement.setAccountDestiny(accountDestiny);
        atmMovement.setMovementType(atmMovement.getMovementType());
        atmMovement.setValue(atmMovement.getValue());

        this.atmMovementRepository.save(atmMovement);

        // Account Movement
        AccountMovement accountMovement = new AccountMovement(
                atmMovement.getDateTransaction(),
                atmMovement.getAccount(),
                atmMovement.getAccountDestiny(),
                atmMovement.getMovementType(),
                atmMovement.getValue(),
                atmMovement.getMovementType().toString());

        // SAQUE
        if (atmMovement.getMovementType().equals(MovementType.SAQUE)) {
            Money money = this.withdrawService.withdraw(atmOpening, atmMovement.getValue());
            atmOpening.setCurrent(money);

            // ATM Opening
            this.atmOpeningService.updateAtmOpening(atm, atmOpening);

            // Money
            this.moneyService.save(money);

            // Account balance
            account.setBalance(account.getBalance() - atmMovement.getValue());

            // DEPOSITO
        } else if (atmMovement.getMovementType().equals(MovementType.DEPOSITO)) {
            // Account balance
            account.setBalance(account.getBalance() + atmMovement.getValue());

            // TRANSFERENCIA
        } else if (atmMovement.getMovementType().equals(MovementType.TRANSFERENCIA)) {
            // Account balance [origin]
            account.setBalance(account.getBalance() - atmMovement.getValue());

            // Description transferencia
            accountMovement.setDescription(atmMovement.getMovementType().toString()
                    + " De CONTA DEBITO - Agencia: " + account.getAgency()
                    + " | Conta: " + account.getNumber()
                    + " | Valor: " + atmMovement.getValue()
                    + " | Para CONTA CREDITO - Agencia: " + accountDestiny.getAgency()
                    + " | Conta: " + accountDestiny.getNumber());

            // Account Movement [destiny]
            AccountMovement accountMovementDestiny = new AccountMovement(
                    atmMovement.getDateTransaction(),
                    atmMovement.getAccountDestiny(),
                    null,
                    atmMovement.getMovementType(),
                    atmMovement.getValue(),
                    atmMovement.getMovementType().toString());

            this.accountMovementService.save(accountMovementDestiny);

            // Account balance [destiny]
            accountDestiny.setBalance(accountDestiny.getBalance() + atmMovement.getValue());

            this.accountService.save(accountDestiny);

        } else {
            throw new ServiceException(INVALID_OPERATION);
        }

        // Account
        this.accountService.save(account);

        // Account Movement
        this.accountMovementService.save(accountMovement);

        // ATM Movement
        this.atmMovementRepository.save(atmMovement);

        return atmMovement;
    }

    @Transactional(readOnly = true)
    public AtmMovement findById(Long id) {
        return this.atmMovementRepository.getOne(id);
    }
}

package br.com.caixa.service;

import br.com.caixa.model.Atm;
import br.com.caixa.model.AtmOpening;
import br.com.caixa.model.Money;
import br.com.caixa.repository.AtmOpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
@Transactional
public class AtmOpeningService {
    private static final String ID_INVALID = "AtmOpening ID invalid";
    private static final String NOT_FOUND = "AtmOpening not found";
    private static final String ATM_NULL = "ATM is null";
    private static final String ATM_INVALID = "ATM ID invalid";
    private static final String MONEY_NULL = "Money is null";
    private static final String MONEY_INVALID = "Money invalid";

    @Autowired
    private AtmOpeningRepository atmOpeningRepository;

    @Autowired
    private AtmService atmService;

    @Autowired
    private MoneyService moneyService;

    public AtmOpeningService() {
    }

    @Transactional(readOnly = true)
    public AtmOpening findById(Long id) {

        if ((id == null) || (id <= 0)) {
            throw new ServiceException(ID_INVALID);
        }

        AtmOpening atmOpening = null;
        try {
            atmOpening = this.atmOpeningRepository.getOne(id);

        } catch (EntityNotFoundException e) {
            throw new ServiceException(NOT_FOUND);

        } catch (ServiceException e) {
            throw new ServiceException(NOT_FOUND);
        }
        return atmOpening;
    }

    @Transactional(readOnly = true)
    public AtmOpening findByActiveAndAtm(Boolean active, Atm atm) {

        if (atm == null) {
            throw new ServiceException(ATM_NULL);

        } else if (atm.getId() <= 0) {
            throw new ServiceException(ATM_INVALID);
        }

        AtmOpening atmOpening = null;
        try {
            atmOpening = this.atmOpeningRepository.findByActiveAndAtm(active, atm);

        } catch (EntityNotFoundException e) {
            throw new ServiceException(NOT_FOUND);

        } catch (ServiceException e) {
            throw new ServiceException(NOT_FOUND);
        }
        return atmOpening;
    }

    public void save(AtmOpening atmOpening) {
        this.atmOpeningRepository.save(atmOpening);
    }

    public String openAtm(Atm atm, Money money) {

        if (money == null) {
            throw new ServiceException(MONEY_NULL);

        } else if (money.getFive().equals(0) &&
                money.getTen().equals(0) &&
                money.getTwenty().equals(0) &&
                money.getFifty().equals(0) &&
                money.getHundred().equals(0)) {

            throw new ServiceException(MONEY_INVALID);
        }

        atm.setActive(true);
        this.atmService.save(atm);

        this.moneyService.save(money);

        AtmOpening atmOpening = new AtmOpening();
        atmOpening.setAtm(atm);
        atmOpening.setActive(true);
        atmOpening.setOpeningDate(new Date());
        atmOpening.setClosingDate(null);
        atmOpening.setBegin(money);
        atmOpening.setCurrent(money);
        atmOpening.setClose(null);

        this.save(atmOpening);

        return "OPENED";
    }

    public String closeAtm(Atm atm) {

        if (atm == null) {
            throw new ServiceException(ATM_NULL);

        } else if (atm.getId() <= 0) {
            throw new ServiceException(ATM_INVALID);
        }

        atm.setActive(false);
        this.atmService.save(atm);

        AtmOpening atmOpening = findByActiveAndAtm(true, atm);

        Money money = atmOpening.getCurrent();

        atmOpening.setActive(false);
        atmOpening.setClosingDate(new Date());
        atmOpening.setClose(money);

        this.save(atmOpening);

        return "CLOSED";
    }

    public void updateAtmOpening(Atm atm, AtmOpening atmOpening) {
        this.save(atmOpening);
    }
}

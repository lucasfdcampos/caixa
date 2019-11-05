package br.com.caixa.service;

import br.com.caixa.model.Atm;
import br.com.caixa.model.AtmOpening;
import br.com.caixa.model.Money;
import br.com.caixa.repository.AtmOpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class AtmOpeningService {

    @Autowired
    private AtmOpeningRepository atmOpeningRepository;

    private AtmService atmService;
    private MoneyService moneyService;


    public AtmOpeningService() {
    }

    public AtmOpeningService(AtmOpeningRepository atmOpeningRepository, MoneyService moneyService) {
        super();
        this.atmOpeningRepository = atmOpeningRepository;
        this.moneyService = moneyService;
    }

    @Transactional(readOnly = true)
    public AtmOpening findById(Long id) {
        return this.atmOpeningRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public AtmOpening findByActiveAndAtm(Boolean active, Atm atm) {
        return this.atmOpeningRepository.findByActiveAndAtm(active, atm);
    }

    /**
     * Metodo responsavel pela abertura do Caixa ATM
     * @todo implementar tratamento de mensagens e excessoes
     */
    public String openAtm(Atm atm, Money money) {
        atm.setActive(true);
        AtmOpening atmOpening = new AtmOpening(atm, true, new Date(), null, money, money, null);
        this.moneyService.save(money);
        atmOpeningRepository.save(atmOpening);
        this.atmService.save(atm);

        return "OPENED";
    }

    /**
     * Metodo responsavel pelo fechamento do Caixa ATM
     * @todo implementar tratamento de mensagens e excessoes
     */
    public String closeAtm(Atm atm) {
        AtmOpening atmOpening = findByActiveAndAtm(true, atm);
        atm.setActive(false);
        Money money = atmOpening.getCurrent();
        atmOpening.setClose(money);
        atmOpening.setClosingDate(new Date());
        atmOpening.setActive(false);
        this.atmOpeningRepository.save(atmOpening);
        this.atmService.save(atm);

        return "CLOSED";
    }

    /**
     *
     * Metodo responsavel por atualizar o Caixa ATM
     * @todo implementar tratamento de mensagens e excessoes
     */
    public String updateAtmOpening(Atm atm, Money money) {
        AtmOpening atmOpening = findByActiveAndAtm(true, atm);
        atmOpening.setCurrent(money);
        this.atmOpeningRepository.save(atmOpening);

        return "UPDATED";
    }










}

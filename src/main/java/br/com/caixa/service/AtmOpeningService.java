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

    @Autowired
    private AtmService atmService;

    @Autowired
    private MoneyService moneyService;

    public AtmOpeningService() {
    }

    @Transactional(readOnly = true)
    public AtmOpening findById(Long id) {
        return this.atmOpeningRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public AtmOpening findByActiveAndAtm(Boolean active, Atm atm) {
        return this.atmOpeningRepository.findByActiveAndAtm(active, atm);
    }

    public void save(AtmOpening atmOpening) {
        this.atmOpeningRepository.save(atmOpening);
    }

    /**
     * Metodo responsavel pela abertura do Caixa ATM
     * @todo implementar tratamento de mensagens e excessoes
     */
    public String openAtm(Atm atm, Money money) {
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

    /**
     * Metodo responsavel pelo fechamento do Caixa ATM
     * @todo implementar tratamento de mensagens e excessoes
     */
    public String closeAtm(Atm atm) {
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

    /**
     *
     * Metodo responsavel por atualizar o Caixa ATM
     * @todo implementar tratamento de mensagens e excessoes
     */
    public String updateAtmOpening(Atm atm, Money money) {
        AtmOpening atmOpening = findByActiveAndAtm(true, atm);
        atmOpening.setCurrent(money);

        this.moneyService.save(money);

        this.save(atmOpening);

        return "UPDATED";
    }
}

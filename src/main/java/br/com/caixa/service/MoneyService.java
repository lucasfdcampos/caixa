package br.com.caixa.service;

import br.com.caixa.model.AtmOpening;
import br.com.caixa.model.Money;
import br.com.caixa.repository.MoneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MoneyService {

    @Autowired
    private MoneyRepository moneyRepository;

    public MoneyService() {
    }

    public MoneyService(MoneyRepository moneyRepository) {
        super();
        this.moneyRepository = moneyRepository;
    }

    public void save(Money money) {
        this.moneyRepository.save(money);
    }

    @Transactional(readOnly = true)
    public Money findById(Long id) {
        return this.moneyRepository.getOne(id);
    }
}

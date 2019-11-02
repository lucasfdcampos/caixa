package br.com.caixa.service;

import br.com.caixa.model.Atm;
import br.com.caixa.repository.AtmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AtmService {

    @Autowired
    private AtmRepository atmRepository;

    public AtmService() {
    }

    public AtmService(AtmRepository atmRepository) {
        super();
        this.atmRepository = atmRepository;
    }

    public void save(Atm atm) {
        this.atmRepository.save(atm);
    }

    @Transactional(readOnly = true)
    public Atm findById(Long id) {
        return this.atmRepository.getOne(id);
    }
}

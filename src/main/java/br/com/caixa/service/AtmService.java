package br.com.caixa.service;

import br.com.caixa.model.Atm;
import br.com.caixa.repository.AtmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class AtmService {
    private static final String ATM_NULL = "Object ATM is null";
    private static final String ATM_INVALID = "ATM invalid";
    private static final String NAME_EMPTY = "Name ATM is empty";
    private static final String ID_INVALID = "ATM ID invalid";
    private static final String NOT_FOUND = "ATM not found";

    @Autowired
    private AtmRepository atmRepository;

    public AtmService() {
    }

    public AtmService(AtmRepository atmRepository) {
        super();
        this.atmRepository = atmRepository;
    }

    public void save(Atm atm) {

        if (atm == null) {
            throw new ServiceException(ATM_NULL);

        } else if ((atm.getName()) == null || (atm.getActive()) == null) {
            throw new ServiceException(ATM_INVALID);

        } else if (atm.getName().isEmpty()) {
            throw new ServiceException(NAME_EMPTY);
        }
        this.atmRepository.save(atm);
    }

    @Transactional(readOnly = true)
    public Atm findById(Long id) {

        if ((id == null) || (id <= 0)) {
            throw new ServiceException(ID_INVALID);
        }

        Atm atm = null;
        try {
            atm = this.atmRepository.getOne(id);

        }catch (EntityNotFoundException e) {
            throw new ServiceException(NOT_FOUND);

        } catch (ServiceException e) {
            throw new ServiceException(NOT_FOUND);
        }
        return atm;
    }
}

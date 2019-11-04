package br.com.caixa.service;

import br.com.caixa.model.Movement;
import br.com.caixa.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;

    public MovementService() {
    }

    public MovementService(MovementRepository movementRepository) {
        super();
        this.movementRepository = movementRepository;
    }

    public void save(Movement movement) {
        this.movementRepository.save(movement);
    }
}

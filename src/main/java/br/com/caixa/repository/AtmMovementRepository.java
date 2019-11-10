package br.com.caixa.repository;

import br.com.caixa.model.AtmMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmMovementRepository extends JpaRepository<AtmMovement, Long> {

}

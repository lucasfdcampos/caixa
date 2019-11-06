package br.com.caixa.repository;

import br.com.caixa.model.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMovementRepository extends JpaRepository<AccountMovement, Long> {

}

package br.com.caixa.repository;

import br.com.caixa.model.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMovementRepository extends JpaRepository<AccountMovement, Long> {

    @Query("select a from AccountMovement a where a.account.id = ?1")
    List<AccountMovement> findAllByAccount(Long id);
}

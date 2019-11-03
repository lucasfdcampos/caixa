package br.com.caixa.repository;

import br.com.caixa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.agency = ?1 and a.number = ?2")
    Account findByAgencyNumber(String agency, String number);
}

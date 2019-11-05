package br.com.caixa.repository;

import br.com.caixa.model.Atm;
import br.com.caixa.model.AtmOpening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmOpeningRepository extends JpaRepository<AtmOpening, Long> {

    @Query("select a from AtmOpening a where a.active = ?1 and a.atm = ?2")
    AtmOpening findByActiveAndAtm(Boolean active, Atm atm);
}

package br.com.caixa.repository;

import br.com.caixa.model.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm, Long> {

}

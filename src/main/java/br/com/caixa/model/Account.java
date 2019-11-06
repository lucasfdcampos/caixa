package br.com.caixa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    public Account() {
    }

    public Account(Long id, String agency, String number, Double balance) {
        this.id = id;
        this.agency = agency;
        this.number = number;
        this.balance = balance;
    }

    @Id
    @JsonIgnore
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 4)
    @Column(nullable = false, name = "agency")
    private String agency;

    @NotNull
    @Size(max = 8)
    @Column(nullable = false, name = "number")
    private String number;

    @NotNull
    @Min(0)
    @Column(nullable = false, name = "balance")
    private Double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = {CascadeType.ALL})
    private List<AccountMovement> accountMovements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<AccountMovement> getAccountMovements() {
        return accountMovements;
    }

    public void setAccountMovements(List<AccountMovement> accountMovements) {
        this.accountMovements = accountMovements;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", agency=" + agency + ", number=" + number +
                ", balance=" + balance + "]";
    }
}

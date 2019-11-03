package br.com.caixa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="movement")
public class Movement implements Serializable {

    private static final long serialVersionUID = 1L;

    public Movement() {
    }

    public Movement(Long id, Date dateTransaction, Account account, Account accountDestiny, MovementType movementType,
                    Double value) {
        this.id = id;
        this.dateTransaction = dateTransaction;
        this.account = account;
        this.accountDestiny = accountDestiny;
        this.movementType = movementType;
        this.value = value;
    }

    @JsonIgnore
    @Id
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTransaction;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account", referencedColumnName = "id")
    private Account account;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountDestiny", referencedColumnName = "id")
    private Account accountDestiny;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name = "movement_type")
    private MovementType movementType;

    @Column(nullable = false, name = "value")
    private Double value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(Account accountDestiny) {
        this.accountDestiny = accountDestiny;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Movement [id=" + id + ", dateTransaction=" + dateTransaction +
                ", account=" + account + ", accountDestiny=" + accountDestiny +
                ", movementType=" + movementType + ", value=" + value + "]";
    }
}

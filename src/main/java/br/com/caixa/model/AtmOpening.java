package br.com.caixa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="atmopening")
public class AtmOpening implements Serializable {

    private static final long serialVersionUID = 1L;

    public AtmOpening() {
    }

    public AtmOpening(@NotNull Atm atm, Boolean active, Date openingDate, Date closingDate, Money begin,
                      Money current, Money close) {
        this.atm = atm;
        this.active = active;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.begin = begin;
        this.current = current;
        this.close = close;
    }

    @Id
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "atm", referencedColumnName = "id")
    private Atm atm;

    @Column(nullable = false, name = "active")
    private Boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    private Date openingDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date closingDate;

    @OneToOne(optional = true)
    @JoinColumn(name = "begin_money", referencedColumnName = "id")
    private Money begin;

    @OneToOne(optional = true)
    @JoinColumn(name = "current_money", referencedColumnName = "id")
    private Money current;

    @OneToOne(optional = true)
    @JoinColumn(name = "close_money", referencedColumnName = "id")
    private Money close;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Atm getAtm() {
        return atm;
    }

    public void setAtm(Atm atm) {
        this.atm = atm;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Money getBegin() {
        return begin;
    }

    public void setBegin(Money begin) {
        this.begin = begin;
    }

    public Money getCurrent() {
        return current;
    }

    public void setCurrent(Money current) {
        this.current = current;
    }

    public Money getClose() {
        return close;
    }

    public void setClose(Money close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "AtmOpening [id=" + id + ", atm=" + atm + ", active=" + active + ", openingDate=" + openingDate +
                ", closingDate=" + closingDate + ", begin=" + begin + ", current=" + current + ", close=" + close + "]";
    }
}

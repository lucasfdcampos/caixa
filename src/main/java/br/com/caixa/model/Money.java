package br.com.caixa.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Esta classe representa a entidade de cedulas
 * @author lucas
 */
@Entity
@Table(name="money")
public class Money implements Serializable {

    private static final long serialVersionUID = 1L;

    public Money() {
    }

    public Money(Integer five, Integer ten, Integer twenty, Integer fifty, Integer hundred) {
        super();
        this.five = five;
        this.ten = ten;
        this.twenty = twenty;
        this.fifty = fifty;
        this.hundred = hundred;
    }

    @Id
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(0)
    @Column(name = "five")
    private Integer five;

    @Min(0)
    @Column(name = "ten")
    private Integer ten;

    @Min(0)
    @Column(name = "twenty")
    private Integer twenty;

    @Min(0)
    @Column(name = "fifty")
    private Integer fifty;

    @Min(0)
    @Column(name = "hundred")
    private Integer hundred;

    @Transient
    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFive() {
        return five;
    }

    public void setFive(Integer five) {
        this.five = five;
    }

    public Integer getTen() {
        return ten;
    }

    public void setTen(Integer ten) {
        this.ten = ten;
    }

    public Integer getTwenty() {
        return twenty;
    }

    public void setTwenty(Integer twenty) {
        this.twenty = twenty;
    }

    public Integer getFifty() {
        return fifty;
    }

    public void setFifty(Integer fifty) {
        this.fifty = fifty;
    }

    public Integer getHundred() {
        return hundred;
    }

    public void setHundred(Integer hundred) {
        this.hundred = hundred;
    }

    public Double totalAmount() {
        return this.amount = (
                (getFive() * 5d) +
                (getTen() * 10d) +
                (getTwenty() * 20d) +
                (getFifty() * 50d) +
                (getHundred() * 100d)
        );
    }

    @Override
    public String toString() {
        return "Money [id=" + id + ", five=" + five + ", ten=" + ten + ", twenty=" + twenty + ", fifty=" + fifty +
                ", hundred=" + hundred + "]";
    }
}

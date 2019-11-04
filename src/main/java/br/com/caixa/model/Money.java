package br.com.caixa.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class represents the banknote entity
 * @author lucas
 */
@Entity
@Table(name="money")
public class Money implements Serializable {

    private static final long serialVersionUID = 1L;

    public Money() {
    }

    public Money(Long id, Integer five, Integer ten, Integer twenty, Integer fifty, Integer hundred) {
        this.id = id;
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

    @Column(nullable = true, name = "five")
    private Integer five;

    @Column(nullable = true, name = "ten")
    private Integer ten;

    @Column(nullable = true, name = "twenty")
    private Integer twenty;

    @Column(nullable = true, name = "fifty")
    private Integer fifty;

    @Column(nullable = true, name = "hundred")
    private Integer hundred;

    @Transient
    private Integer amount;

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

    public Integer totalAmount() {
        return this.amount = (
                (getFive() * 5) +
                (getTen() * 10) +
                (getTwenty() * 20) +
                (getFifty() * 50) +
                (getHundred() * 100)
        );
    }

    @Override
    public String toString() {
        return "Money [id=" + id + ", five=" + five + ", ten=" + ten + ", twenty=" + twenty + ", fifty=" + fifty +
                ", hundred=" + hundred + "]";
    }
}

package br.com.caixa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="atm")
public class Atm implements Serializable {

    private static final long serialVersionUID = 1L;

    public Atm() {
    }

    public Atm(Long id, @NotNull @Size(max = 30) String name, Boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    @Id
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "active")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Atm [id=" + id + ", name=" + name + ", active=" + active + "]";
    }
}

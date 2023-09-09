package com.sym022.sym022.entities;

import com.sym022.sym022.enums.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Dm.selectDmById", query = "SELECT dm from DmEntity dm where dm.idDm = :idDm"),
        @NamedQuery(name = "Dm.selectDmByIdEvent", query = "SELECT dm from DmEntity dm where dm.eventByIdEvent.idEvent = :idEvent"),
})

@Entity
@Table(name = "dm", schema = "sym022")
public class DmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dm", nullable = false)
    private int idDm;

    @Basic
    @NotNull
    @Range(min=1920,max= 2005)
    @Column(name = "year_of_birth", nullable = false)
    private int yearOfBirth;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ethnicity", nullable = false)
    private Ethnicity ethnicity;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "culture", nullable = false)
    private Culture culture;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    /*--- Getters and setters ---*/

    public int getIdDm() {
        return idDm;
    }

    public void setIdDm(int idDm) {
        this.idDm = idDm;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Ethnicity getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Ethnicity ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Culture getCulture() {
        return culture;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }

    /*---HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DmEntity dmEntity = (DmEntity) o;
        return idDm == dmEntity.idDm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDm);
    }
}

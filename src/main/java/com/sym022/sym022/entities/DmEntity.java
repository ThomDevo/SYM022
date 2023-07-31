package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dm", schema = "sym022", catalog = "")
public class DmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dm", nullable = false)
    private int idDm;
    @Basic
    @Column(name = "year_of_birth", nullable = false)
    private int yearOfBirth;
    @Basic
    @Column(name = "sex", nullable = false)
    private Object sex;
    @Basic
    @Column(name = "ethnicity", nullable = false)
    private Object ethnicity;
    @Basic
    @Column(name = "culture", nullable = false)
    private Object culture;
    @Basic
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

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

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }

    public Object getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Object ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Object getCulture() {
        return culture;
    }

    public void setCulture(Object culture) {
        this.culture = culture;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DmEntity dmEntity = (DmEntity) o;
        return idDm == dmEntity.idDm && yearOfBirth == dmEntity.yearOfBirth && idEvent == dmEntity.idEvent && Objects.equals(sex, dmEntity.sex) && Objects.equals(ethnicity, dmEntity.ethnicity) && Objects.equals(culture, dmEntity.culture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDm, yearOfBirth, sex, ethnicity, culture, idEvent);
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }
}

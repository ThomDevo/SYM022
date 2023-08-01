package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "ic", schema = "sym022", catalog = "")
public class IcEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_ic", nullable = false)
    private int idIc;

    @Basic
    @NotNull
    @Column(name = "ic_date", nullable = false)
    private Date icDate;

    @Basic
    @NotNull
    @Column(name = "prot_vers", nullable = false, length = 200)
    private String protVers;

    @Basic
    @NotNull
    @Column(name = "elig_yn", nullable = false)
    private boolean eligYn = false;
    @Basic
    @NotNull
    @Column(name = "ie_not_met", nullable = false)
    private Object ieNotMet;
    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;
    @Basic
    @Column(name = "id_event", nullable = false)
    private int idEvent;

    /*--- Getters and Setters ---*/

    public int getIdIc() {
        return idIc;
    }

    public void setIdIc(int idIc) {
        this.idIc = idIc;
    }

    public Date getIcDate() {
        return icDate;
    }

    public void setIcDate(Date icDate) {
        this.icDate = icDate;
    }

    public String getProtVers() {
        return protVers;
    }

    public void setProtVers(String protVers) {
        this.protVers = protVers;
    }

    public boolean getEligYn() {
        return eligYn;
    }

    public void setEligYn(byte eligYn) {
        this.eligYn = eligYn;
    }

    public void setEligYn(boolean eligYn) {
        this.eligYn = eligYn;
    }

    public Object getIeNotMet() {
        return ieNotMet;
    }

    public void setIeNotMet(Object ieNotMet) {
        this.ieNotMet = ieNotMet;
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    /*--- HashCode and Equal ---*/

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IcEntity icEntity = (IcEntity) o;
        return idIc == icEntity.idIc;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIc);
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }
}

package com.sym022.sym022.entities;

import javax.persistence.*;
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
    @Column(name = "ic_date", nullable = false)
    private Date icDate;
    @Basic
    @Column(name = "prot_vers", nullable = false, length = 200)
    private String protVers;
    @Basic
    @Column(name = "elig_yn", nullable = false)
    private byte eligYn;
    @Basic
    @Column(name = "ie_not_met", nullable = false)
    private Object ieNotMet;
    @Basic
    @Column(name = "id_event", nullable = false)
    private int idEvent;

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

    public byte getEligYn() {
        return eligYn;
    }

    public void setEligYn(byte eligYn) {
        this.eligYn = eligYn;
    }

    public Object getIeNotMet() {
        return ieNotMet;
    }

    public void setIeNotMet(Object ieNotMet) {
        this.ieNotMet = ieNotMet;
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
        IcEntity icEntity = (IcEntity) o;
        return idIc == icEntity.idIc && eligYn == icEntity.eligYn && idEvent == icEntity.idEvent && Objects.equals(icDate, icEntity.icDate) && Objects.equals(protVers, icEntity.protVers) && Objects.equals(ieNotMet, icEntity.ieNotMet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIc, icDate, protVers, eligYn, ieNotMet, idEvent);
    }
}

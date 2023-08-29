package com.sym022.sym022.entities;

import com.sym022.sym022.enums.IeNotMet;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Ic.selectIcById", query = "SELECT ic FROM IcEntity ic WHERE ic.idIc = :idIc"),
        @NamedQuery(name = "Ic.icEligibleYes", query = "SELECT ic FROM IcEntity ic WHERE ic.eligYn = true")
})

@Entity
@Table(name = "ic", schema = "sym022")
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
    private boolean eligYn = true;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ie_not_met", nullable = false)
    private IeNotMet ieNotMet;

    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

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

    public void setEligYn(boolean eligYn) {
        this.eligYn = eligYn;
    }

    public IeNotMet getIeNotMet() {
        return ieNotMet;
    }

    public void setIeNotMet(IeNotMet ieNotMet) {
        this.ieNotMet = ieNotMet;
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }

    /*--- HashCode and Equal ---*/

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

}

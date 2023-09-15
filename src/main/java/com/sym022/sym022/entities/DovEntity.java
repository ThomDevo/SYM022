package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Dov.selectDovById", query = "SELECT dov from DovEntity dov WHERE dov.idDov = :idDov"),
        @NamedQuery(name = "Dov.selectDovByIdEvent", query = "SELECT dov from DovEntity dov WHERE dov.eventByIdEvent.idEvent = :idEvent"),
        @NamedQuery(name = "Dov.selectDovNDMois1", query = "SELECT COUNT(dov) from DovEntity dov WHERE " +
                "(dov.visitYn = false and dov.eventByIdEvent.visitByIdVisit.visitNum = 20 and dov.eventByIdEvent.subjectByIdSubject.idSubject = :idSubject)")
})

@Entity
@Table(name = "dov", schema = "sym022")
public class DovEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dov", nullable = false)
    private int idDov;

    @Basic
    @NotNull
    @Column(name = "visit_yn", nullable = false)
    private boolean visitYn = true;

    @Basic
    @Column(name = "visit_nd", nullable = true, length = 200)
    private String visitNd;

    @Basic
    @Column(name = "visit_date", nullable = true)
    private Date visitDate;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;


    /*---Getters and Setters ---*/

    public int getIdDov() {
        return idDov;
    }

    public void setIdDov(int idDov) {
        this.idDov = idDov;
    }

    public boolean getVisitYn() {
        return visitYn;
    }

    public void setVisitYn(boolean visitYn) {
        this.visitYn = visitYn;
    }

    public String getVisitNd() {
        return visitNd;
    }

    public void setVisitNd(String visitNd) {
        this.visitNd = visitNd;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }

    public boolean isVisitYn() {
        return visitYn;
    }

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DovEntity dovEntity = (DovEntity) o;
        return idDov == dovEntity.idDov;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDov);
    }
}

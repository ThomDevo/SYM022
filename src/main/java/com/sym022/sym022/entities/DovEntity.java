package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "dov", schema = "sym022")
public class DovEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dov", nullable = false)
    private int idDov;

    @Basic
    @NotNull
    @Column(name = "visit_vn", nullable = false)
    private boolean visitVn = false;

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

    public boolean getVisitVn() {
        return visitVn;
    }

    public void setVisitVn(boolean visitVn) {
        this.visitVn = visitVn;
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

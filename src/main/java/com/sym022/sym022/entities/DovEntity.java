package com.sym022.sym022.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "dov", schema = "sym022", catalog = "")
public class DovEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dov", nullable = false)
    private int idDov;
    @Basic
    @Column(name = "visit_vn", nullable = false)
    private byte visitVn;
    @Basic
    @Column(name = "visit_nd", nullable = true, length = 200)
    private String visitNd;
    @Basic
    @Column(name = "visit_date", nullable = true)
    private Date visitDate;
    @Basic
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    public int getIdDov() {
        return idDov;
    }

    public void setIdDov(int idDov) {
        this.idDov = idDov;
    }

    public byte getVisitVn() {
        return visitVn;
    }

    public void setVisitVn(byte visitVn) {
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
        DovEntity dovEntity = (DovEntity) o;
        return idDov == dovEntity.idDov && visitVn == dovEntity.visitVn && idEvent == dovEntity.idEvent && Objects.equals(visitNd, dovEntity.visitNd) && Objects.equals(visitDate, dovEntity.visitDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDov, visitVn, visitNd, visitDate, idEvent);
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }
}

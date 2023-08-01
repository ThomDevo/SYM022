package com.sym022.sym022.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "te", schema = "sym022", catalog = "")
public class TeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_te", nullable = false)
    private int idTe;
    @Basic
    @Column(name = "te_yn", nullable = false)
    private boolean teYn;
    @Basic
    @Column(name = "te_nd", nullable = true, length = 200)
    private String teNd;
    @Basic
    @Column(name = "te_date", nullable = true)
    private Date teDate;
    @Basic
    @Column(name = "target_lesions", nullable = false)
    private Object targetLesions;
    @Basic
    @Column(name = "non_target_lesions", nullable = false)
    private Object nonTargetLesions;
    @Basic
    @Column(name = "new_lesions", nullable = false)
    private boolean newLesions;
    @Basic
    @Column(name = "overall_response", nullable = false)
    private Object overallResponse;
    @Basic
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    public int getIdTe() {
        return idTe;
    }

    public void setIdTe(int idTe) {
        this.idTe = idTe;
    }

    public boolean getTeYn() {
        return teYn;
    }

    public void setTeYn(byte teYn) {
        this.teYn = teYn;
    }

    public void setTeYn(boolean teYn) {
        this.teYn = teYn;
    }

    public String getTeNd() {
        return teNd;
    }

    public void setTeNd(String teNd) {
        this.teNd = teNd;
    }

    public Date getTeDate() {
        return teDate;
    }

    public void setTeDate(Date teDate) {
        this.teDate = teDate;
    }

    public Object getTargetLesions() {
        return targetLesions;
    }

    public void setTargetLesions(Object targetLesions) {
        this.targetLesions = targetLesions;
    }

    public Object getNonTargetLesions() {
        return nonTargetLesions;
    }

    public void setNonTargetLesions(Object nonTargetLesions) {
        this.nonTargetLesions = nonTargetLesions;
    }

    public boolean getNewLesions() {
        return newLesions;
    }

    public void setNewLesions(byte newLesions) {
        this.newLesions = newLesions;
    }

    public void setNewLesions(boolean newLesions) {
        this.newLesions = newLesions;
    }

    public Object getOverallResponse() {
        return overallResponse;
    }

    public void setOverallResponse(Object overallResponse) {
        this.overallResponse = overallResponse;
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
        TeEntity teEntity = (TeEntity) o;
        return idTe == teEntity.idTe && teYn == teEntity.teYn && newLesions == teEntity.newLesions && idEvent == teEntity.idEvent && Objects.equals(teNd, teEntity.teNd) && Objects.equals(teDate, teEntity.teDate) && Objects.equals(targetLesions, teEntity.targetLesions) && Objects.equals(nonTargetLesions, teEntity.nonTargetLesions) && Objects.equals(overallResponse, teEntity.overallResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTe, teYn, teNd, teDate, targetLesions, nonTargetLesions, newLesions, overallResponse, idEvent);
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }
}

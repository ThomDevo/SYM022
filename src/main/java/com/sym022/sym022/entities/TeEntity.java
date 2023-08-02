package com.sym022.sym022.entities;

import com.sym022.sym022.enums.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "te", schema = "sym022")
public class TeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_te", nullable = false)
    private int idTe;

    @Basic
    @NotNull
    @Column(name = "te_yn", nullable = false)
    private boolean teYn;

    @Basic
    @Column(name = "te_nd", nullable = true, length = 200)
    private String teNd;

    @Basic
    @Column(name = "te_date", nullable = true)
    private Date teDate;

    @Basic
    @NotNull
    @Column(name = "target_lesions", nullable = false)
    private TargetLesionsOverallResponse targetLesions;

    @Basic
    @NotNull
    @Column(name = "non_target_lesions", nullable = false)
    private NonTargetLesions nonTargetLesions;

    @Basic
    @NotNull
    @Column(name = "new_lesions", nullable = false)
    private boolean newLesions;

    @Basic
    @NotNull
    @Column(name = "overall_response", nullable = false)
    private TargetLesionsOverallResponse overallResponse;

    @ManyToOne
    @NotNull
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

    public TargetLesionsOverallResponse getTargetLesions() {
        return targetLesions;
    }

    public void setTargetLesions(TargetLesionsOverallResponse targetLesions) {
        this.targetLesions = targetLesions;
    }

    public NonTargetLesions getNonTargetLesions() {
        return nonTargetLesions;
    }

    public void setNonTargetLesions(NonTargetLesions nonTargetLesions) {
        this.nonTargetLesions = nonTargetLesions;
    }

    public boolean getNewLesions() {
        return newLesions;
    }

    public void setNewLesions(boolean newLesions) {
        this.newLesions = newLesions;
    }

    public TargetLesionsOverallResponse getOverallResponse() {
        return overallResponse;
    }

    public void setOverallResponse(TargetLesionsOverallResponse overallResponse) {
        this.overallResponse = overallResponse;
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
        TeEntity teEntity = (TeEntity) o;
        return idTe == teEntity.idTe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTe);
    }
}

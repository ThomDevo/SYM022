package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "visit", schema = "sym022", catalog = "")
public class VisitEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_visit", nullable = false)
    private int idVisit;
    @Basic
    @Column(name = "visit_num", nullable = false)
    private int visitNum;
    @Basic
    @Column(name = "visit_label", nullable = false)
    private Object visitLabel;
    @OneToMany(mappedBy = "visitByIdVisit")
    private Collection<EventEntity> eventsByIdVisit;

    public int getIdVisit() {
        return idVisit;
    }

    public void setIdVisit(int idVisit) {
        this.idVisit = idVisit;
    }

    public int getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }

    public Object getVisitLabel() {
        return visitLabel;
    }

    public void setVisitLabel(Object visitLabel) {
        this.visitLabel = visitLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitEntity that = (VisitEntity) o;
        return idVisit == that.idVisit && visitNum == that.visitNum && Objects.equals(visitLabel, that.visitLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVisit, visitNum, visitLabel);
    }

    public Collection<EventEntity> getEventsByIdVisit() {
        return eventsByIdVisit;
    }

    public void setEventsByIdVisit(Collection<EventEntity> eventsByIdVisit) {
        this.eventsByIdVisit = eventsByIdVisit;
    }
}

package com.sym022.sym022.entities;

import com.sym022.sym022.enums.VisitLabel;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Visit.selectVisitAll", query = "SELECT vi FROM VisitEntity vi ORDER BY vi.visitNum ASC"),
        @NamedQuery(name = "Visit.selectVisitById", query = "SELECT vi FROM VisitEntity vi WHERE vi.idVisit = :idVisit"),
        @NamedQuery(name = "Visit.selectVisitByNum", query = "SELECT vi FROM VisitEntity vi WHERE vi.visitNum = :visitNum"),
        @NamedQuery(name = "Visit.selectVisitByLabel", query = "SELECT vi FROM VisitEntity vi WHERE vi.visitLabel = :visitLabel"),
        @NamedQuery(name = "Visit.isVisitNumExist", query = "SELECT COUNT(vi) FROM VisitEntity vi WHERE vi.visitNum = :visitNum"),
        @NamedQuery(name = "Visit.isVisitLabelExist", query = "SELECT COUNT(vi) FROM VisitEntity vi WHERE vi.visitLabel = :visitLabel"),
        @NamedQuery(name = "Visit.selectVisitAeCm", query = "SELECT vi FROM VisitEntity vi WHERE vi.visitNum in (80,90)"),
        @NamedQuery(name = "Visit.selectVisitExceptMois1", query = "SELECT vi FROM VisitEntity vi WHERE vi.visitNum != 20"),
        @NamedQuery(name = "Visit.selectVisitExceptScreening", query = "SELECT vi FROM VisitEntity vi WHERE vi.visitNum != 10")
})

@Entity
@Table(name = "visit", schema = "sym022")
public class VisitEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_visit", nullable = false)
    private int idVisit;

    @Basic
    @Range(min=1,max= 99)
    @NotNull
    @Column(name = "visit_num", nullable = false)
    private int visitNum;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visit_label", nullable = false)
    private VisitLabel visitLabel;

    @OneToMany(mappedBy = "visitByIdVisit")
    private List<EventEntity> eventsByIdVisit;

    /*--- Getters and Setters ---*/

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

    public VisitLabel getVisitLabel() {
        return visitLabel;
    }

    public void setVisitLabel(VisitLabel visitLabel) {
        this.visitLabel = visitLabel;
    }

    public List<EventEntity> getEventsByIdVisit() {
        return eventsByIdVisit;
    }

    public void setEventsByIdVisit(List<EventEntity> eventsByIdVisit) {
        this.eventsByIdVisit = eventsByIdVisit;
    }

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitEntity that = (VisitEntity) o;
        return idVisit == that.idVisit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVisit);
    }
}

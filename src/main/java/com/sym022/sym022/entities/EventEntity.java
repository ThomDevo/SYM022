package com.sym022.sym022.entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.List;
import java.util.Objects;


@NamedQueries(value = {
        @NamedQuery(name = "Event.selectEventAll", query = "SELECT ev FROM EventEntity ev ORDER BY ev.idEvent ASC"),
        @NamedQuery(name = "Event.selectEventById", query = "SELECT ev FROM EventEntity ev WHERE ev.idEvent = :idEvent"),
        @NamedQuery(name = "Event.selectEventWithQuery", query = "SELECT ev FROM EventEntity ev WHERE ev.queried = true "),
        @NamedQuery(name = "Event.selectCountEventOccurrence", query = "SELECT ev FROM EventEntity ev WHERE (ev.subjectByIdSubject.idSubject = :idSubject AND ev.formByIdForm.idForm = :idForm)"),
        @NamedQuery(name = "Event.CheckNewPatient", query = "SELECT ev FROM EventEntity ev WHERE ev.subjectByIdSubject.idSubject = :idSubject"),
        @NamedQuery(name = "Event.CheckIcCompleted", query = "SELECT ev FROM EventEntity ev WHERE ev.subjectByIdSubject.idSubject = :idSubject AND ev.formByIdForm.formNum = 20"),
        @NamedQuery(name = "Event.CheckMois1Started", query = "SELECT ev FROM EventEntity ev WHERE ev.subjectByIdSubject.idSubject = :idSubject AND ev.visitByIdVisit.visitNum = 20 AND ev.formByIdForm.formNum = 10"),
        @NamedQuery(name = "Event.CheckSubjectCompleted", query = "SELECT ev FROM EventEntity ev where ev.subjectByIdSubject.idSubject = :idSubject AND ev.formByIdForm.formNum NOT IN (80, 90)")
})

@Entity
@Table(name = "event", schema = "sym022")
public class EventEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;

    @Basic
    @NotNull
    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    @Basic
    @NotNull
    @Column(name = "queried", nullable = false)
    private boolean queried = false;

    @Basic
    @NotNull
    @Column(name = "monitored", nullable = false)
    private boolean monitored = false;

    @Basic
    @NotNull
    @Range(min=0,max= 99999)
    @Column(name = "\"order\"", nullable = false)
    private int order;

    @Basic
    @NotNull
    @Column(name = "\"available\"", nullable = false)
    private boolean available = true;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject", nullable = false)
    private SubjectEntity subjectByIdSubject;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_visit", referencedColumnName = "id_visit", nullable = false)
    private VisitEntity visitByIdVisit;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_form", referencedColumnName = "id_form", nullable = false)
    private FormEntity formByIdForm;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<AeEntity> aesByIdEvent;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<AuditTrailEntity> auditTrailsByIdEvent;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<CmEntity> cmsByIdEvent;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<DmEntity> dmsByIdEvent;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<DovEntity> dovsByIdEvent;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<QueryEntity> queriesByIdEvent;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<TeEntity> tesByIdEvent;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<VsEntity> vsByIdEvent;

    @OneToMany(mappedBy = "eventByIdEvent")
    private List<IcEntity> icsByIdEvent;


    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    /*--- Getters and setters ---*/

    public boolean getCompleted() {
        return completed;
    }

    public boolean getQueried() {
        return queried;
    }

    public boolean getMonitored() {
        return monitored;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean getAvailable() {
        return available;
    }

    public List<AeEntity> getAesByIdEvent() {
        return aesByIdEvent;
    }

    public void setAesByIdEvent(List<AeEntity> aesByIdEvent) {
        this.aesByIdEvent = aesByIdEvent;
    }

    public List<AuditTrailEntity> getAuditTrailsByIdEvent() {
        return auditTrailsByIdEvent;
    }

    public void setAuditTrailsByIdEvent(List<AuditTrailEntity> auditTrailsByIdEvent) {
        this.auditTrailsByIdEvent = auditTrailsByIdEvent;
    }

    public List<CmEntity> getCmsByIdEvent() {
        return cmsByIdEvent;
    }

    public void setCmsByIdEvent(List<CmEntity> cmsByIdEvent) {
        this.cmsByIdEvent = cmsByIdEvent;
    }

    public List<DmEntity> getDmsByIdEvent() {
        return dmsByIdEvent;
    }

    public void setDmsByIdEvent(List<DmEntity> dmsByIdEvent) {
        this.dmsByIdEvent = dmsByIdEvent;
    }

    public List<DovEntity> getDovsByIdEvent() {
        return dovsByIdEvent;
    }

    public void setDovsByIdEvent(List<DovEntity> dovsByIdEvent) {
        this.dovsByIdEvent = dovsByIdEvent;
    }

    public SubjectEntity getSubjectByIdSubject() {
        return subjectByIdSubject;
    }

    public void setSubjectByIdSubject(SubjectEntity subjectByIdSubject) {
        this.subjectByIdSubject = subjectByIdSubject;
    }

    public VisitEntity getVisitByIdVisit() {
        return visitByIdVisit;
    }

    public void setVisitByIdVisit(VisitEntity visitByIdVisit) {
        this.visitByIdVisit = visitByIdVisit;
    }

    public FormEntity getFormByIdForm() {
        return formByIdForm;
    }

    public void setFormByIdForm(FormEntity formByIdForm) {
        this.formByIdForm = formByIdForm;
    }

    public List<QueryEntity> getQueriesByIdEvent() {
        return queriesByIdEvent;
    }

    public void setQueriesByIdEvent(List<QueryEntity> queriesByIdEvent) {
        this.queriesByIdEvent = queriesByIdEvent;
    }

    public List<TeEntity> getTesByIdEvent() {
        return tesByIdEvent;
    }

    public void setTesByIdEvent(List<TeEntity> tesByIdEvent) {
        this.tesByIdEvent = tesByIdEvent;
    }

    public List<VsEntity> getVsByIdEvent() {
        return vsByIdEvent;
    }

    public void setVsByIdEvent(List<VsEntity> vsByIdEvent) {
        this.vsByIdEvent = vsByIdEvent;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isQueried() {
        return queried;
    }

    public void setQueried(boolean queried) {
        this.queried = queried;
    }

    public boolean isMonitored() {
        return monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

    public List<IcEntity> getIcsByIdEvent() {
        return icsByIdEvent;
    }

    public void setIcsByIdEvent(List<IcEntity> icsByIdEvent) {
        this.icsByIdEvent = icsByIdEvent;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity that = (EventEntity) o;
        return idEvent == that.idEvent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvent);
    }
}

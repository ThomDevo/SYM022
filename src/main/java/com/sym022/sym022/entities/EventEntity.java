package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "event", schema = "sym022", catalog = "")
public class EventEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;

    @Basic
    @Column(name = "completed", nullable = false)
    private boolean completed = false;
    @Basic
    @Column(name = "queried", nullable = false)
    private boolean queried = false;
    @Basic
    @Column(name = "monitored", nullable = false)
    private boolean monitored = false;
    @Basic
    @Column(name = "order", nullable = false)
    private int order;
    @Basic
    @Column(name = "available", nullable = false)
    private boolean available = false;
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
    private List<QueryEntity> queriesByIdEvent;
    @OneToMany(mappedBy = "eventByIdEvent")
    private List<TeEntity> tesByIdEvent;
    @OneToMany(mappedBy = "eventByIdEvent")
    private List<VsEntity> vsByIdEvent;
    @Basic
    @Column(name = "id_subject", nullable = false)
    private int idSubject;
    @Basic
    @Column(name = "id_visit", nullable = false)
    private int idVisit;
    @Basic
    @Column(name = "id_form", nullable = false)
    private int idForm;
    @OneToMany(mappedBy = "eventByIdEvent")
    private Collection<IcEntity> icsByIdEvent;
    

    /*--- Getters and setters ---*/

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(byte completed) {
        this.completed = completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean getQueried() {
        return queried;
    }

    public void setQueried(byte queried) {
        this.queried = queried;
    }

    public void setQueried(boolean queried) {
        this.queried = queried;
    }

    public boolean getMonitored() {
        return monitored;
    }

    public void setMonitored(byte monitored) {
        this.monitored = monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
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

    public void setAvailable(byte available) {
        this.available = available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

    /*--- HashCode and Equal ---*/

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

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public int getIdVisit() {
        return idVisit;
    }

    public void setIdVisit(int idVisit) {
        this.idVisit = idVisit;
    }

    public int getIdForm() {
        return idForm;
    }

    public void setIdForm(int idForm) {
        this.idForm = idForm;
    }

    public Collection<IcEntity> getIcsByIdEvent() {
        return icsByIdEvent;
    }

    public void setIcsByIdEvent(Collection<IcEntity> icsByIdEvent) {
        this.icsByIdEvent = icsByIdEvent;
    }
}

package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Collection;
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
    private byte completed;
    @Basic
    @Column(name = "queried", nullable = false)
    private byte queried;
    @Basic
    @Column(name = "monitored", nullable = false)
    private byte monitored;
    @Basic
    @Column(name = "order", nullable = false)
    private int order;
    @Basic
    @Column(name = "available", nullable = false)
    private byte available;
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
    private Collection<AeEntity> aesByIdEvent;
    @OneToMany(mappedBy = "eventByIdEvent")
    private Collection<AuditTrailEntity> auditTrailsByIdEvent;
    @OneToMany(mappedBy = "eventByIdEvent")
    private Collection<CmEntity> cmsByIdEvent;
    @OneToMany(mappedBy = "eventByIdEvent")
    private Collection<DmEntity> dmsByIdEvent;
    @OneToMany(mappedBy = "eventByIdEvent")
    private Collection<DovEntity> dovsByIdEvent;
    @ManyToOne
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject", nullable = false)
    private SubjectEntity subjectByIdSubject;
    @ManyToOne
    @JoinColumn(name = "id_visit", referencedColumnName = "id_visit", nullable = false)
    private VisitEntity visitByIdVisit;
    @ManyToOne
    @JoinColumn(name = "id_form", referencedColumnName = "id_form", nullable = false)
    private FormEntity formByIdForm;
    @OneToMany(mappedBy = "eventByIdEvent")
    private List<QueryEntity> queriesByIdEvent;
    @OneToMany(mappedBy = "eventByIdEvent")
    private List<TeEntity> tesByIdEvent;
    @OneToMany(mappedBy = "eventByIdEvent")
    private List<VsEntity> vsByIdEvent;

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public byte getCompleted() {
        return completed;
    }

    public void setCompleted(byte completed) {
        this.completed = completed;
    }

    public byte getQueried() {
        return queried;
    }

    public void setQueried(byte queried) {
        this.queried = queried;
    }

    public byte getMonitored() {
        return monitored;
    }

    public void setMonitored(byte monitored) {
        this.monitored = monitored;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public byte getAvailable() {
        return available;
    }

    public void setAvailable(byte available) {
        this.available = available;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity that = (EventEntity) o;
        return idEvent == that.idEvent && completed == that.completed && queried == that.queried && monitored == that.monitored && order == that.order && available == that.available && idSubject == that.idSubject && idVisit == that.idVisit && idForm == that.idForm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvent, completed, queried, monitored, order, available, idSubject, idVisit, idForm);
    }

    public Collection<AeEntity> getAesByIdEvent() {
        return aesByIdEvent;
    }

    public void setAesByIdEvent(Collection<AeEntity> aesByIdEvent) {
        this.aesByIdEvent = aesByIdEvent;
    }

    public Collection<AuditTrailEntity> getAuditTrailsByIdEvent() {
        return auditTrailsByIdEvent;
    }

    public void setAuditTrailsByIdEvent(Collection<AuditTrailEntity> auditTrailsByIdEvent) {
        this.auditTrailsByIdEvent = auditTrailsByIdEvent;
    }

    public Collection<CmEntity> getCmsByIdEvent() {
        return cmsByIdEvent;
    }

    public void setCmsByIdEvent(Collection<CmEntity> cmsByIdEvent) {
        this.cmsByIdEvent = cmsByIdEvent;
    }

    public Collection<DmEntity> getDmsByIdEvent() {
        return dmsByIdEvent;
    }

    public void setDmsByIdEvent(Collection<DmEntity> dmsByIdEvent) {
        this.dmsByIdEvent = dmsByIdEvent;
    }

    public Collection<DovEntity> getDovsByIdEvent() {
        return dovsByIdEvent;
    }

    public void setDovsByIdEvent(Collection<DovEntity> dovsByIdEvent) {
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

    public Collection<QueryEntity> getQueriesByIdEvent() {
        return queriesByIdEvent;
    }

    public void setQueriesByIdEvent(List<QueryEntity> queriesByIdEvent) {
        this.queriesByIdEvent = queriesByIdEvent;
    }

    public Collection<TeEntity> getTesByIdEvent() {
        return tesByIdEvent;
    }

    public void setTesByIdEvent(List<TeEntity> tesByIdEvent) {
        this.tesByIdEvent = tesByIdEvent;
    }

    public Collection<VsEntity> getVsByIdEvent() {
        return vsByIdEvent;
    }

    public void setVsByIdEvent(List<VsEntity> vsByIdEvent) {
        this.vsByIdEvent = vsByIdEvent;
    }
}

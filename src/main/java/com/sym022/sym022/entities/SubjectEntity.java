package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "subject", schema = "sym022", catalog = "")
public class SubjectEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_subject", nullable = false)
    private int idSubject;
    @Basic
    @Column(name = "subject_num", nullable = false)
    private int subjectNum;
    @Basic
    @Column(name = "subject_status", nullable = false)
    private byte subjectStatus;
    @Basic
    @Column(name = "id_site", nullable = false)
    private int idSite;
    @OneToMany(mappedBy = "subjectByIdSubject")
    private Collection<EventEntity> eventsByIdSubject;
    @ManyToOne
    @JoinColumn(name = "id_site", referencedColumnName = "id_site", nullable = false)
    private SiteEntity siteByIdSite;

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public int getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(int subjectNum) {
        this.subjectNum = subjectNum;
    }

    public byte getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(byte subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectEntity that = (SubjectEntity) o;
        return idSubject == that.idSubject && subjectNum == that.subjectNum && subjectStatus == that.subjectStatus && idSite == that.idSite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSubject, subjectNum, subjectStatus, idSite);
    }

    public Collection<EventEntity> getEventsByIdSubject() {
        return eventsByIdSubject;
    }

    public void setEventsByIdSubject(Collection<EventEntity> eventsByIdSubject) {
        this.eventsByIdSubject = eventsByIdSubject;
    }

    public SiteEntity getSiteByIdSite() {
        return siteByIdSite;
    }

    public void setSiteByIdSite(SiteEntity siteByIdSite) {
        this.siteByIdSite = siteByIdSite;
    }
}

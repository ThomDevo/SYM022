package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Subject.selectSubjectAll", query = "SELECT su FROM SubjectEntity su ORDER BY su.subjectNum ASC"),
        @NamedQuery(name = "Subject.selectSubjectById", query = "SELECT su FROM SubjectEntity su WHERE su.idSubject = :idSubject"),
        @NamedQuery(name = "Subject.selectSubjectByNum", query = "SELECT su FROM SubjectEntity su WHERE su.subjectNum = :subjectNum"),
        @NamedQuery(name = "Subject.isSubjectNumExist", query = "SELECT COUNT(su) FROM SubjectEntity su WHERE su.subjectNum = :subjectNum")

})

@Entity
@Table(name = "subject", schema = "sym022")
public class SubjectEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_subject", nullable = false)
    private int idSubject;

    @Basic
    @NotNull
    @Column(name = "subject_num", nullable = false)
    private int subjectNum;

    @Basic
    @NotNull
    @Column(name = "subject_status", nullable = false)
    private boolean subjectStatus;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_site", referencedColumnName = "id_site", nullable = false)
    private SiteEntity siteByIdSite;

    @OneToMany(mappedBy = "subjectByIdSubject")
    private List<EventEntity> eventsByIdSubject;

    /*--- Getters and Setters ---*/

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

    public boolean getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(boolean subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

    public List<EventEntity> getEventsByIdSubject() {
        return eventsByIdSubject;
    }
    public void setEventsByIdSubject(List<EventEntity> eventsByIdSubject) {
        this.eventsByIdSubject = eventsByIdSubject;
    }

    public SiteEntity getSiteByIdSite() {
        return siteByIdSite;
    }

    public void setSiteByIdSite(SiteEntity siteByIdSite) {
        this.siteByIdSite = siteByIdSite;
    }

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectEntity that = (SubjectEntity) o;
        return idSubject == that.idSubject;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSubject);
    }
}

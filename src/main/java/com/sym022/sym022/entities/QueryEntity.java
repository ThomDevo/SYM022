package com.sym022.sym022.entities;

import com.sym022.sym022.enums.QueryStatus;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Query.selectConsult", query = "SELECT q from QueryEntity q JOIN UserSiteEntity usu " +
                "ON (q.eventByIdEvent.subjectByIdSubject.siteByIdSite.idSite = usu.siteByIdSite.idSite) " +
                "WHERE usu.userByIdUser.idUser = :idUser AND ((lower(q.queryStatus) LIKE CONCAT('%', :researchWord, '%')) " +
                "OR (lower(q.eventByIdEvent.subjectByIdSubject.subjectNum) LIKE CONCAT ('%', :researchWord, '%')) " +
                "OR (lower(q.eventByIdEvent.visitByIdVisit.visitLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.eventByIdEvent.formByIdForm.formLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.userByIdUser.username) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.queryText) LIKE CONCAT ('%', :researchWord, '%'))) ORDER BY q.queryDatetime"),
        @NamedQuery(name = "Query.selectOpenedDM", query = "SELECT q from QueryEntity q JOIN UserSiteEntity usu " +
                "ON (q.eventByIdEvent.subjectByIdSubject.siteByIdSite.idSite = usu.siteByIdSite.idSite) " +
                "WHERE usu.userByIdUser.idUser = :idUser AND q.queryStatus = 'OPENED'" +
                "AND ((lower(q.eventByIdEvent.subjectByIdSubject.subjectNum) LIKE CONCAT ('%', :researchWord, '%')) " +
                "OR (lower(q.eventByIdEvent.visitByIdVisit.visitLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.eventByIdEvent.formByIdForm.formLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.userByIdUser.username) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.queryText) LIKE CONCAT ('%', :researchWord, '%'))) ORDER BY q.queryDatetime DESC"),
        @NamedQuery(name = "Query.selectOpenedRole", query = "SELECT q from QueryEntity q JOIN UserSiteEntity usu " +
                "ON (q.eventByIdEvent.subjectByIdSubject.siteByIdSite.idSite = usu.siteByIdSite.idSite) " +
                "WHERE usu.userByIdUser.idUser = :idUser AND q.userByIdUser.roleByIdRole.idRole = :idRole AND q.queryStatus = 'OPENED'" +
                "AND ((lower(q.eventByIdEvent.subjectByIdSubject.subjectNum) LIKE CONCAT ('%', :researchWord, '%')) " +
                "OR (lower(q.eventByIdEvent.visitByIdVisit.visitLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.eventByIdEvent.formByIdForm.formLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.userByIdUser.username) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.queryText) LIKE CONCAT ('%', :researchWord, '%'))) ORDER BY q.queryDatetime DESC"),
        @NamedQuery(name = "Query.selectAnsweredDM", query = "SELECT q from QueryEntity q JOIN UserSiteEntity usu " +
                "ON (q.eventByIdEvent.subjectByIdSubject.siteByIdSite.idSite = usu.siteByIdSite.idSite) " +
                "WHERE usu.userByIdUser.idUser = :idUser AND q.queryStatus = 'ANSWERED'" +
                "AND ((lower(q.eventByIdEvent.subjectByIdSubject.subjectNum) LIKE CONCAT ('%', :researchWord, '%')) " +
                "OR (lower(q.eventByIdEvent.visitByIdVisit.visitLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.eventByIdEvent.formByIdForm.formLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.userByIdUser.username) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.queryText) LIKE CONCAT ('%', :researchWord, '%'))) ORDER BY q.queryDatetime DESC"),
        @NamedQuery(name = "Query.selectAnsweredRole", query = "SELECT q from QueryEntity q JOIN UserSiteEntity usu " +
                "ON (q.eventByIdEvent.subjectByIdSubject.siteByIdSite.idSite = usu.siteByIdSite.idSite) " +
                "WHERE usu.userByIdUser.idUser = :idUser AND q.userByIdUser.roleByIdRole.idRole = :idRole AND q.queryStatus = 'ANSWERED'" +
                "AND ((lower(q.eventByIdEvent.subjectByIdSubject.subjectNum) LIKE CONCAT ('%', :researchWord, '%')) " +
                "OR (lower(q.eventByIdEvent.visitByIdVisit.visitLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.eventByIdEvent.formByIdForm.formLabel) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.userByIdUser.username) LIKE CONCAT ('%', :researchWord, '%'))" +
                "OR (lower(q.queryText) LIKE CONCAT ('%', :researchWord, '%'))) ORDER BY q.queryDatetime DESC"),
        @NamedQuery(name = "Query.selectByEvent", query = "SELECT q from QueryEntity q WHERE q.eventByIdEvent.idEvent = :idEvent")
})

@Entity
@Table(name = "query", schema = "sym022")
public class QueryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_query", nullable = false)
    private int idQuery;

    @Basic
    @Column(name = "query_datetime", nullable = false)
    private Date queryDatetime;

    @Basic
    @Column(name = "query_text", nullable = false, length = 200)
    private String queryText;

    @Basic
    @Column(name = "query_answer", nullable = true, length = 200)
    private String queryAnswer;

    @Basic
    @Column(name = "query_closed", nullable = true)
    private boolean queryClosed = false;

    @Basic
    @Column(name = "query_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private QueryStatus queryStatus;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UserEntity userByIdUser;

    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    /*--- Getters and Setters ---*/

    public int getIdQuery() {
        return idQuery;
    }

    public void setIdQuery(int idQuery) {
        this.idQuery = idQuery;
    }

    public Date getQueryDatetime() {
        return queryDatetime;
    }

    public void setQueryDatetime(Date queryDatetime) {
        this.queryDatetime = queryDatetime;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getQueryAnswer() {
        return queryAnswer;
    }

    public void setQueryAnswer(String queryAnswer) {
        this.queryAnswer = queryAnswer;
    }

    public Boolean getQueryClosed() {
        return queryClosed;
    }

    public void setQueryClosed(Boolean queryClosed) {
        this.queryClosed = queryClosed;
    }

    public QueryStatus getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(QueryStatus queryStatus) {
        this.queryStatus = queryStatus;
    }

    public UserEntity getUserByIdUser() {
        return userByIdUser;
    }

    public void setUserByIdUser(UserEntity userByIdUser) {
        this.userByIdUser = userByIdUser;
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
        QueryEntity that = (QueryEntity) o;
        return idQuery == that.idQuery;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuery);
    }
}

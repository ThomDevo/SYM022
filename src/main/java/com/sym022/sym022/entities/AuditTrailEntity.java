package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "AuditTrail.findAuditTrailByCharacteristic", query = "SELECT au from AuditTrailEntity au " +
                " JOIN UserSiteEntity usu ON (au.eventByIdEvent.subjectByIdSubject.siteByIdSite.idSite = usu.siteByIdSite.idSite)" +
                " where(usu.userByIdUser.idUser = :idUser) AND " +
                "((lower(au.userByIdUser.lastName )like concat('%', :researchWord, '%')) or" +
                " (lower(au.userByIdUser.username )like concat('%', :researchWord, '%')) or " +
                " (lower(au.userByIdUser.firstName )like concat('%', :researchWord, '%')) or" +
                " (lower(au.eventByIdEvent.formByIdForm.formLabel )like concat('%', :researchWord, '%')) or" +
                " (lower(au.eventByIdEvent.visitByIdVisit.visitLabel) like concat('%', :researchWord, '%'))) ORDER BY au.auditTrailDatetime DESC"),
        @NamedQuery(name = "AuditTrail.selectAuditTrailById", query = "SELECT au from AuditTrailEntity au WHERE au.idAuditTrail = :idAuditTrail"),

})

@Entity
@Table(name = "audit_trail", schema = "sym022")
public class AuditTrailEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_audit_trail", nullable = false)
    private int idAuditTrail;

    @Basic
    @NotNull
    @Column(name = "audit_trail_datetime", nullable = false)
    private Date auditTrailDatetime;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UserEntity userByIdUser;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    /*---Getters and setters---*/

    public int getIdAuditTrail() {
        return idAuditTrail;
    }

    public void setIdAuditTrail(int idAuditTrail) {
        this.idAuditTrail = idAuditTrail;
    }

    public Date getAuditTrailDatetime() {
        return auditTrailDatetime;
    }

    public void setAuditTrailDatetime(Date auditTrailDatetime) {
        this.auditTrailDatetime = auditTrailDatetime;
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

    /*---HasCode and equals ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditTrailEntity that = (AuditTrailEntity) o;
        return idAuditTrail == that.idAuditTrail;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAuditTrail);
    }
}

package com.sym022.sym022.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "cm", schema = "sym022", catalog = "")
public class CmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cm", nullable = false)
    private int idCm;
    @Basic
    @Column(name = "cmterm", nullable = false, length = 200)
    private String cmterm;
    @Basic
    @Column(name = "cmtermc", nullable = true, length = 200)
    private String cmtermc;
    @Basic
    @Column(name = "cmstdat", nullable = false)
    private Date cmstdat;
    @Basic
    @Column(name = "cmong", nullable = false)
    private byte cmong;
    @Basic
    @Column(name = "cmendat", nullable = true)
    private Date cmendat;
    @Basic
    @Column(name = "cmindic", nullable = false)
    private Object cmindic;
    @Basic
    @Column(name = "cmindicsp", nullable = true, length = 200)
    private String cmindicsp;
    @Basic
    @Column(name = "cmdose", nullable = true, precision = 0)
    private Double cmdose;
    @Basic
    @Column(name = "cmdosu", nullable = false)
    private Object cmdosu;
    @Basic
    @Column(name = "cmdosusp", nullable = true, length = 200)
    private String cmdosusp;
    @Basic
    @Column(name = "cmroute", nullable = false)
    private Object cmroute;
    @Basic
    @Column(name = "cmroutesp", nullable = true, length = 200)
    private String cmroutesp;
    @Basic
    @Column(name = "cmfreq", nullable = false)
    private Object cmfreq;
    @Basic
    @Column(name = "cmfreqsp", nullable = true, length = 200)
    private String cmfreqsp;
    @Basic
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    public int getIdCm() {
        return idCm;
    }

    public void setIdCm(int idCm) {
        this.idCm = idCm;
    }

    public String getCmterm() {
        return cmterm;
    }

    public void setCmterm(String cmterm) {
        this.cmterm = cmterm;
    }

    public String getCmtermc() {
        return cmtermc;
    }

    public void setCmtermc(String cmtermc) {
        this.cmtermc = cmtermc;
    }

    public Date getCmstdat() {
        return cmstdat;
    }

    public void setCmstdat(Date cmstdat) {
        this.cmstdat = cmstdat;
    }

    public byte getCmong() {
        return cmong;
    }

    public void setCmong(byte cmong) {
        this.cmong = cmong;
    }

    public Date getCmendat() {
        return cmendat;
    }

    public void setCmendat(Date cmendat) {
        this.cmendat = cmendat;
    }

    public Object getCmindic() {
        return cmindic;
    }

    public void setCmindic(Object cmindic) {
        this.cmindic = cmindic;
    }

    public String getCmindicsp() {
        return cmindicsp;
    }

    public void setCmindicsp(String cmindicsp) {
        this.cmindicsp = cmindicsp;
    }

    public Double getCmdose() {
        return cmdose;
    }

    public void setCmdose(Double cmdose) {
        this.cmdose = cmdose;
    }

    public Object getCmdosu() {
        return cmdosu;
    }

    public void setCmdosu(Object cmdosu) {
        this.cmdosu = cmdosu;
    }

    public String getCmdosusp() {
        return cmdosusp;
    }

    public void setCmdosusp(String cmdosusp) {
        this.cmdosusp = cmdosusp;
    }

    public Object getCmroute() {
        return cmroute;
    }

    public void setCmroute(Object cmroute) {
        this.cmroute = cmroute;
    }

    public String getCmroutesp() {
        return cmroutesp;
    }

    public void setCmroutesp(String cmroutesp) {
        this.cmroutesp = cmroutesp;
    }

    public Object getCmfreq() {
        return cmfreq;
    }

    public void setCmfreq(Object cmfreq) {
        this.cmfreq = cmfreq;
    }

    public String getCmfreqsp() {
        return cmfreqsp;
    }

    public void setCmfreqsp(String cmfreqsp) {
        this.cmfreqsp = cmfreqsp;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmEntity cmEntity = (CmEntity) o;
        return idCm == cmEntity.idCm && cmong == cmEntity.cmong && idEvent == cmEntity.idEvent && Objects.equals(cmterm, cmEntity.cmterm) && Objects.equals(cmtermc, cmEntity.cmtermc) && Objects.equals(cmstdat, cmEntity.cmstdat) && Objects.equals(cmendat, cmEntity.cmendat) && Objects.equals(cmindic, cmEntity.cmindic) && Objects.equals(cmindicsp, cmEntity.cmindicsp) && Objects.equals(cmdose, cmEntity.cmdose) && Objects.equals(cmdosu, cmEntity.cmdosu) && Objects.equals(cmdosusp, cmEntity.cmdosusp) && Objects.equals(cmroute, cmEntity.cmroute) && Objects.equals(cmroutesp, cmEntity.cmroutesp) && Objects.equals(cmfreq, cmEntity.cmfreq) && Objects.equals(cmfreqsp, cmEntity.cmfreqsp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCm, cmterm, cmtermc, cmstdat, cmong, cmendat, cmindic, cmindicsp, cmdose, cmdosu, cmdosusp, cmroute, cmroutesp, cmfreq, cmfreqsp, idEvent);
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }
}

package com.sym022.sym022.entities;

import com.sym022.sym022.enums.Cmdosu;
import com.sym022.sym022.enums.Cmfreq;
import com.sym022.sym022.enums.Cmindic;
import com.sym022.sym022.enums.Cmroute;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "cm", schema = "sym022")
public class CmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cm", nullable = false)
    private int idCm;

    @Basic
    @NotNull
    @Column(name = "cmterm", nullable = false, length = 200)
    private String cmterm;

    @Basic
    @Column(name = "cmtermc", nullable = true, length = 200)
    private String cmtermc;

    @Basic
    @NotNull
    @Column(name = "cmstdat", nullable = false)
    private Date cmstdat;

    @Basic
    @NotNull
    @Column(name = "cmong", nullable = false)
    private boolean cmong = false;

    @Basic
    @Column(name = "cmendat", nullable = true)
    private Date cmendat;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cmindic", nullable = false)
    private Cmindic cmindic;

    @Basic
    @Column(name = "cmindicsp", nullable = true, length = 200)
    private String cmindicsp;

    @Basic
    @Column(name = "cmdose", nullable = true, precision = 0)
    private Double cmdose;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cmdosu", nullable = false)
    private Cmdosu cmdosu;

    @Basic
    @Column(name = "cmdosusp", nullable = true, length = 200)
    private String cmdosusp;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cmroute", nullable = false)
    private Cmroute cmroute;

    @Basic
    @Column(name = "cmroutesp", nullable = true, length = 200)
    private String cmroutesp;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cmfreq", nullable = false)
    private Cmfreq cmfreq;

    @Basic
    @Column(name = "cmfreqsp", nullable = true, length = 200)
    private String cmfreqsp;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    /*---Getters and setters ---*/

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

    public boolean getCmong() {
        return cmong;
    }

    public void setCmong(boolean cmong) {
        this.cmong = cmong;
    }

    public Date getCmendat() {
        return cmendat;
    }

    public void setCmendat(Date cmendat) {
        this.cmendat = cmendat;
    }

    public Cmindic getCmindic() {
        return cmindic;
    }

    public void setCmindic(Cmindic cmindic) {
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

    public Cmdosu getCmdosu() {
        return cmdosu;
    }

    public void setCmdosu(Cmdosu cmdosu) {
        this.cmdosu = cmdosu;
    }

    public String getCmdosusp() {
        return cmdosusp;
    }

    public void setCmdosusp(String cmdosusp) {
        this.cmdosusp = cmdosusp;
    }

    public Cmroute getCmroute() {
        return cmroute;
    }

    public void setCmroute(Cmroute cmroute) {
        this.cmroute = cmroute;
    }

    public String getCmroutesp() {
        return cmroutesp;
    }

    public void setCmroutesp(String cmroutesp) {
        this.cmroutesp = cmroutesp;
    }

    public Cmfreq getCmfreq() {
        return cmfreq;
    }

    public void setCmfreq(Cmfreq cmfreq) {
        this.cmfreq = cmfreq;
    }

    public String getCmfreqsp() {
        return cmfreqsp;
    }

    public void setCmfreqsp(String cmfreqsp) {
        this.cmfreqsp = cmfreqsp;
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }

    /*---HasCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmEntity cmEntity = (CmEntity) o;
        return idCm == cmEntity.idCm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCm);
    }
}

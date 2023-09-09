package com.sym022.sym022.entities;

import com.sym022.sym022.enums.*;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.NotNull;

@NamedQueries(value = {
        @NamedQuery(name = "Ae.selectAeById", query = "SELECT ae from AeEntity ae where ae.idAe = :idAe"),
})

@Entity
@Table(name = "ae", schema = "sym022")
public class AeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_ae", nullable = false)
    private int idAe;

    @Basic
    @Column(name = "aeterm", nullable = false, length = 200)
    private String aeterm;

    @Basic
    @Column(name = "aetermc", nullable = true, length = 200)
    private String aetermc;

    @Basic
    @Column(name = "aestdat", nullable = false)
    private Date aestdat;

    @Basic
    @Column(name = "aeout", nullable = false)
    @Enumerated(EnumType.STRING)
    private Aeout aeout;

    @Basic
    @Column(name = "aeendat", nullable = true)
    private Date aeendat;

    @Basic
    @Column(name = "aetoxgd", nullable = false)
    @Enumerated(EnumType.STRING)
    private Aetoxgd aetoxgd;

    @Basic
    @Column(name = "aesev", nullable = false)
    @Enumerated(EnumType.STRING)
    private Aesev aesev;

    @Basic
    @Column(name = "aerel", nullable = false)
    @Enumerated(EnumType.STRING)
    private Aerel aerel;

    @Basic
    @Column(name = "aeacn", nullable = false)
    @Enumerated(EnumType.STRING)
    private Aeacn aeacn;

    @Basic
    @Column(name = "aecm", nullable = false)
    private boolean aecm = false;

    @Basic
    @Column(name = "aeproc", nullable = false)
    private boolean aeproc;

    @Basic
    @Column(name = "aeother", nullable = false)
    private boolean aeother = false;

    @Basic
    @Column(name = "aeothersp", nullable = true, length = 200)
    private String aeothersp;

    @Basic
    @Column(name = "aeser", nullable = false)
    private boolean aeser = false;

    @Basic
    @Column(name = "aedeath", nullable = false)
    private boolean aedeath = false;

    @Basic
    @Column(name = "aelife", nullable = false)
    private boolean aelife = false;

    @Basic
    @Column(name = "aehosp", nullable = false)
    private boolean aehosp = false;

    @Basic
    @Column(name = "aedisab", nullable = false)
    private boolean aedisab = false;

    @Basic
    @Column(name = "aecong", nullable = false)
    private boolean aecong = false;

    @Basic
    @Column(name = "aemedim", nullable = false)
    private boolean aemedim = false;

    @Basic
    @Column(name = "aemedimsp", nullable = true, length = 200)
    private String aemedimsp;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    /*---Getters and Setters---*/

    public int getIdAe() {
        return idAe;
    }

    public void setIdAe(int idAe) {
        this.idAe = idAe;
    }

    public String getAeterm() {
        return aeterm;
    }

    public void setAeterm(String aeterm) {
        this.aeterm = aeterm;
    }

    public String getAetermc() {
        return aetermc;
    }

    public void setAetermc(String aetermc) {
        this.aetermc = aetermc;
    }

    public Date getAestdat() {
        return aestdat;
    }

    public void setAestdat(java.sql.Date aestdat) {
        this.aestdat = aestdat;
    }

    public void setAestdat(Date aestdat) {
        this.aestdat = aestdat;
    }

    public Aeout getAeout() {
        return aeout;
    }

    public void setAeout(Aeout aeout) {
        this.aeout = aeout;
    }

    public Date getAeendat() {
        return aeendat;
    }

    public void setAeendat(java.sql.Date aeendat) {
        this.aeendat = aeendat;
    }

    public void setAeendat(Date aeendat) {
        this.aeendat = aeendat;
    }

    public Aetoxgd getAetoxgd() {
        return aetoxgd;
    }

    public void setAetoxgd(Aetoxgd aetoxgd) {
        this.aetoxgd = aetoxgd;
    }

    public Aesev getAesev() {
        return aesev;
    }

    public void setAesev(Aesev aesev) {
        this.aesev = aesev;
    }

    public Aerel getAerel() {
        return aerel;
    }

    public void setAerel(Aerel aerel) {
        this.aerel = aerel;
    }

    public Aeacn getAeacn() {
        return aeacn;
    }

    public void setAeacn(Aeacn aeacn) {
        this.aeacn = aeacn;
    }

    public boolean isAecm() {
        return aecm;
    }

    public void setAecm(boolean aecm) {
        this.aecm = aecm;
    }

    public boolean isAeproc() {
        return aeproc;
    }

    public void setAeproc(boolean aeproc) {
        this.aeproc = aeproc;
    }

    public boolean isAeother() {
        return aeother;
    }

    public void setAeother(boolean aeother) {
        this.aeother = aeother;
    }

    public String getAeothersp() {
        return aeothersp;
    }

    public void setAeothersp(String aeothersp) {
        this.aeothersp = aeothersp;
    }

    public boolean isAeser() {
        return aeser;
    }

    public void setAeser(boolean aeser) {
        this.aeser = aeser;
    }

    public boolean isAedeath() {
        return aedeath;
    }

    public void setAedeath(boolean aedeath) {
        this.aedeath = aedeath;
    }

    public boolean isAelife() {
        return aelife;
    }

    public void setAelife(boolean aelife) {
        this.aelife = aelife;
    }

    public boolean isAehosp() {
        return aehosp;
    }

    public void setAehosp(boolean aehosp) {
        this.aehosp = aehosp;
    }

    public boolean isAedisab() {
        return aedisab;
    }

    public void setAedisab(boolean aedisab) {
        this.aedisab = aedisab;
    }

    public boolean isAecong() {
        return aecong;
    }

    public void setAecong(boolean aecong) {
        this.aecong = aecong;
    }

    public boolean isAemedim() {
        return aemedim;
    }

    public void setAemedim(boolean aemedim) {
        this.aemedim = aemedim;
    }

    public String getAemedimsp() {
        return aemedimsp;
    }

    public void setAemedimsp(String aemedimsp) {
        this.aemedimsp = aemedimsp;
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }

    /*---Equals and HashCode ---*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AeEntity aeEntity = (AeEntity) o;
        return idAe == aeEntity.idAe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAe);
    }

}

package com.sym022.sym022.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "vs", schema = "sym022", catalog = "")
public class VsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_vs", nullable = false)
    private int idVs;
    @Basic
    @Column(name = "vs_yn", nullable = false)
    private boolean vsYn;
    @Basic
    @Column(name = "vs_nd", nullable = true, length = 200)
    private String vsNd;
    @Basic
    @Column(name = "vs_date", nullable = true)
    private Date vsDate;
    @Basic
    @Column(name = "height_nd", nullable = false)
    private boolean heightNd;
    @Basic
    @Column(name = "height", nullable = true, precision = 0)
    private Double height;
    @Basic
    @Column(name = "height_u", nullable = false)
    private Object heightU;
    @Basic
    @Column(name = "weight_nd", nullable = false)
    private boolean weightNd;
    @Basic
    @Column(name = "weight", nullable = true)
    private Integer weight;
    @Basic
    @Column(name = "weight_u", nullable = false)
    private Object weightU;
    @Basic
    @Column(name = "bp_nd", nullable = false)
    private boolean bpNd;
    @Basic
    @Column(name = "sbp", nullable = true)
    private Integer sbp;
    @Basic
    @Column(name = "dbp", nullable = true)
    private Integer dbp;
    @Basic
    @Column(name = "hr_nd", nullable = false)
    private boolean hrNd;
    @Basic
    @Column(name = "hr", nullable = true)
    private Integer hr;
    @Basic
    @Column(name = "rr_nd", nullable = false)
    private boolean rrNd;
    @Basic
    @Column(name = "rr", nullable = true)
    private Integer rr;
    @Basic
    @Column(name = "temp_nd", nullable = true)
    private boolean tempNd;
    @Basic
    @Column(name = "temp", nullable = true, precision = 0)
    private Double temp;
    @Basic
    @Column(name = "temp_u", nullable = false)
    private Object tempU;
    @Basic
    @Column(name = "temp_route", nullable = true)
    private Object tempRoute;
    @Basic
    @Column(name = "oxysat_nd", nullable = false)
    private boolean oxysatNd;
    @Basic
    @Column(name = "oxysat", nullable = true)
    private Integer oxysat;
    @Basic
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    public int getIdVs() {
        return idVs;
    }

    public void setIdVs(int idVs) {
        this.idVs = idVs;
    }

    public boolean getVsYn() {
        return vsYn;
    }

    public void setVsYn(byte vsYn) {
        this.vsYn = vsYn;
    }

    public void setVsYn(boolean vsYn) {
        this.vsYn = vsYn;
    }

    public String getVsNd() {
        return vsNd;
    }

    public void setVsNd(String vsNd) {
        this.vsNd = vsNd;
    }

    public Date getVsDate() {
        return vsDate;
    }

    public void setVsDate(Date vsDate) {
        this.vsDate = vsDate;
    }

    public boolean getHeightNd() {
        return heightNd;
    }

    public void setHeightNd(byte heightNd) {
        this.heightNd = heightNd;
    }

    public void setHeightNd(boolean heightNd) {
        this.heightNd = heightNd;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Object getHeightU() {
        return heightU;
    }

    public void setHeightU(Object heightU) {
        this.heightU = heightU;
    }

    public boolean getWeightNd() {
        return weightNd;
    }

    public void setWeightNd(byte weightNd) {
        this.weightNd = weightNd;
    }

    public void setWeightNd(boolean weightNd) {
        this.weightNd = weightNd;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Object getWeightU() {
        return weightU;
    }

    public void setWeightU(Object weightU) {
        this.weightU = weightU;
    }

    public boolean getBpNd() {
        return bpNd;
    }

    public void setBpNd(byte bpNd) {
        this.bpNd = bpNd;
    }

    public void setBpNd(boolean bpNd) {
        this.bpNd = bpNd;
    }

    public Integer getSbp() {
        return sbp;
    }

    public void setSbp(Integer sbp) {
        this.sbp = sbp;
    }

    public Integer getDbp() {
        return dbp;
    }

    public void setDbp(Integer dbp) {
        this.dbp = dbp;
    }

    public boolean getHrNd() {
        return hrNd;
    }

    public void setHrNd(byte hrNd) {
        this.hrNd = hrNd;
    }

    public void setHrNd(boolean hrNd) {
        this.hrNd = hrNd;
    }

    public Integer getHr() {
        return hr;
    }

    public void setHr(Integer hr) {
        this.hr = hr;
    }

    public boolean getRrNd() {
        return rrNd;
    }

    public void setRrNd(byte rrNd) {
        this.rrNd = rrNd;
    }

    public void setRrNd(boolean rrNd) {
        this.rrNd = rrNd;
    }

    public Integer getRr() {
        return rr;
    }

    public void setRr(Integer rr) {
        this.rr = rr;
    }

    public boolean getTempNd() {
        return tempNd;
    }

    public void setTempNd(Byte tempNd) {
        this.tempNd = tempNd;
    }

    public void setTempNd(boolean tempNd) {
        this.tempNd = tempNd;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Object getTempU() {
        return tempU;
    }

    public void setTempU(Object tempU) {
        this.tempU = tempU;
    }

    public Object getTempRoute() {
        return tempRoute;
    }

    public void setTempRoute(Object tempRoute) {
        this.tempRoute = tempRoute;
    }

    public boolean getOxysatNd() {
        return oxysatNd;
    }

    public void setOxysatNd(byte oxysatNd) {
        this.oxysatNd = oxysatNd;
    }

    public void setOxysatNd(boolean oxysatNd) {
        this.oxysatNd = oxysatNd;
    }

    public Integer getOxysat() {
        return oxysat;
    }

    public void setOxysat(Integer oxysat) {
        this.oxysat = oxysat;
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
        VsEntity vsEntity = (VsEntity) o;
        return idVs == vsEntity.idVs && vsYn == vsEntity.vsYn && heightNd == vsEntity.heightNd && weightNd == vsEntity.weightNd && bpNd == vsEntity.bpNd && hrNd == vsEntity.hrNd && rrNd == vsEntity.rrNd && oxysatNd == vsEntity.oxysatNd && idEvent == vsEntity.idEvent && Objects.equals(vsNd, vsEntity.vsNd) && Objects.equals(vsDate, vsEntity.vsDate) && Objects.equals(height, vsEntity.height) && Objects.equals(heightU, vsEntity.heightU) && Objects.equals(weight, vsEntity.weight) && Objects.equals(weightU, vsEntity.weightU) && Objects.equals(sbp, vsEntity.sbp) && Objects.equals(dbp, vsEntity.dbp) && Objects.equals(hr, vsEntity.hr) && Objects.equals(rr, vsEntity.rr) && Objects.equals(tempNd, vsEntity.tempNd) && Objects.equals(temp, vsEntity.temp) && Objects.equals(tempU, vsEntity.tempU) && Objects.equals(tempRoute, vsEntity.tempRoute) && Objects.equals(oxysat, vsEntity.oxysat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVs, vsYn, vsNd, vsDate, heightNd, height, heightU, weightNd, weight, weightU, bpNd, sbp, dbp, hrNd, hr, rrNd, rr, tempNd, temp, tempU, tempRoute, oxysatNd, oxysat, idEvent);
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }
}

package com.sym022.sym022.entities;

import com.sym022.sym022.enums.HeightU;
import com.sym022.sym022.enums.TempRoute;
import com.sym022.sym022.enums.TempU;
import com.sym022.sym022.enums.WeightU;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "vs", schema = "sym022")
public class VsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_vs", nullable = false)
    private int idVs;

    @Basic
    @NotNull
    @Column(name = "vs_yn", nullable = false)
    private boolean vsYn = false;

    @Basic
    @Column(name = "vs_nd", nullable = true, length = 200)
    private String vsNd;

    @Basic
    @Column(name = "vs_date", nullable = true)
    private Date vsDate;

    @Basic
    @NotNull
    @Column(name = "height_nd", nullable = false)
    private boolean heightNd = false;

    @Basic
    @Range(min=1,max= 999)
    @Column(name = "height", nullable = true, precision = 4,scale = 1)
    private Double height;

    @Basic
    @NotNull
    @Column(name = "height_u", nullable = false)
    private HeightU heightU;

    @Basic
    @NotNull
    @Column(name = "weight_nd", nullable = false)
    private boolean weightNd = false;

    @Basic
    @Range(min=1,max= 999)
    @Column(name = "weight", nullable = true, precision = 4,scale = 1)
    private Double weight;

    @Basic
    @NotNull
    @Column(name = "weight_u", nullable = false)
    private WeightU weightU;

    @Basic
    @NotNull
    @Column(name = "bp_nd", nullable = false)
    private boolean bpNd = false;

    @Basic
    @Range(min=1,max= 999)
    @Column(name = "sbp", nullable = true)
    private int sbp;

    @Basic
    @Range(min=1,max= 999)
    @Column(name = "dbp", nullable = true)
    private int dbp;

    @Basic
    @NotNull
    @Column(name = "hr_nd", nullable = false)
    private boolean hrNd = false;

    @Basic
    @Range(min=1,max= 300)
    @Column(name = "hr", nullable = true)
    private int hr;

    @Basic
    @Column(name = "rr_nd", nullable = false)
    private boolean rrNd = false;

    @Basic
    @Range(min=1,max= 150)
    @Column(name = "rr", nullable = true)
    private int rr;

    @Basic
    @Column(name = "temp_nd", nullable = true)
    private boolean tempNd = false;

    @Basic
    @Range(min=1,max= 300)
    @Column(name = "temp", nullable = true, precision = 4,scale = 1)
    private Double temp;

    @Basic
    @Column(name = "temp_u", nullable = false)
    private TempU tempU;

    @Basic
    @Column(name = "temp_route", nullable = true)
    private TempRoute tempRoute;

    @Basic
    @Column(name = "oxysat_nd", nullable = false)
    private boolean oxysatNd = false;

    @Basic
    @Range(min=0,max= 100)
    @Column(name = "oxysat", nullable = true)
    private int oxysat;

    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventEntity eventByIdEvent;

    /*--- Getters and Setters ---*/

    public int getIdVs() {
        return idVs;
    }

    public void setIdVs(int idVs) {
        this.idVs = idVs;
    }

    public boolean getVsYn() {
        return vsYn;
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

    public void setHeightNd(boolean heightNd) {
        this.heightNd = heightNd;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public HeightU getHeightU() {
        return heightU;
    }

    public void setHeightU(HeightU heightU) {
        this.heightU = heightU;
    }

    public boolean getWeightNd() {
        return weightNd;
    }

    public void setWeightNd(boolean weightNd) {
        this.weightNd = weightNd;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public WeightU getWeightU() {
        return weightU;
    }

    public void setWeightU(WeightU weightU) {
        this.weightU = weightU;
    }

    public boolean getBpNd() {
        return bpNd;
    }

    public void setBpNd(boolean bpNd) {
        this.bpNd = bpNd;
    }

    public int getSbp() {
        return sbp;
    }

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public boolean getHrNd() {
        return hrNd;
    }

    public void setHrNd(boolean hrNd) {
        this.hrNd = hrNd;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public boolean getRrNd() {
        return rrNd;
    }

    public void setRrNd(boolean rrNd) {
        this.rrNd = rrNd;
    }

    public int getRr() {
        return rr;
    }

    public void setRr(int rr) {
        this.rr = rr;
    }

    public boolean getTempNd() {
        return tempNd;
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

    public TempU getTempU() {
        return tempU;
    }

    public void setTempU(TempU tempU) {
        this.tempU = tempU;
    }

    public TempRoute getTempRoute() {
        return tempRoute;
    }

    public void setTempRoute(TempRoute tempRoute) {
        this.tempRoute = tempRoute;
    }

    public boolean getOxysatNd() {
        return oxysatNd;
    }

    public void setOxysatNd(boolean oxysatNd) {
        this.oxysatNd = oxysatNd;
    }

    public int getOxysat() {
        return oxysat;
    }

    public void setOxysat(int oxysat) {
        this.oxysat = oxysat;
    }

    public EventEntity getEventByIdEvent() {
        return eventByIdEvent;
    }

    public void setEventByIdEvent(EventEntity eventByIdEvent) {
        this.eventByIdEvent = eventByIdEvent;
    }

    public boolean isVsYn() {
        return vsYn;
    }

    public boolean isHeightNd() {
        return heightNd;
    }

    public boolean isWeightNd() {
        return weightNd;
    }

    public boolean isBpNd() {
        return bpNd;
    }

    public boolean isHrNd() {
        return hrNd;
    }

    public boolean isRrNd() {
        return rrNd;
    }

    public boolean isTempNd() {
        return tempNd;
    }

    public boolean isOxysatNd() {
        return oxysatNd;
    }

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VsEntity vsEntity = (VsEntity) o;
        return idVs == vsEntity.idVs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVs);
    }
}

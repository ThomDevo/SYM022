package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "site", schema = "sym022", catalog = "")
public class SiteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_site", nullable = false)
    private int idSite;
    @Basic
    @Column(name = "site_num", nullable = false)
    private int siteNum;
    @Basic
    @Column(name = "site_name", nullable = false, length = 200)
    private String siteName;
    @Basic
    @Column(name = "pi_name", nullable = false, length = 200)
    private String piName;
    @Basic
    @Column(name = "site_status", nullable = true)
    private boolean siteStatus;
    @OneToMany(mappedBy = "siteByIdSite")
    private List<SubjectEntity> subjectsByIdSite;
    @OneToMany(mappedBy = "siteByIdSite")
    private List<UserSiteEntity> userSitesByIdSite;

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public int getSiteNum() {
        return siteNum;
    }

    public void setSiteNum(int siteNum) {
        this.siteNum = siteNum;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPiName() {
        return piName;
    }

    public void setPiName(String piName) {
        this.piName = piName;
    }

    public boolean getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(boolean siteStatus) {
        this.siteStatus = siteStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteEntity that = (SiteEntity) o;
        return idSite == that.idSite && siteNum == that.siteNum && Objects.equals(siteName, that.siteName) && Objects.equals(piName, that.piName) && Objects.equals(siteStatus, that.siteStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSite, siteNum, siteName, piName, siteStatus);
    }

    public List<SubjectEntity> getSubjectsByIdSite() {
        return subjectsByIdSite;
    }

    public void setSubjectsByIdSite(List<SubjectEntity> subjectsByIdSite) {
        this.subjectsByIdSite = subjectsByIdSite;
    }

    public List<UserSiteEntity> getUserSitesByIdSite() {
        return userSitesByIdSite;
    }

    public void setUserSitesByIdSite(List<UserSiteEntity> userSitesByIdSite) {
        this.userSitesByIdSite = userSitesByIdSite;
    }
}

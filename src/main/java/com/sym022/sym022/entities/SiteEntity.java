package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Site.selectSiteAll", query = "SELECT si FROM SiteEntity si ORDER BY si.siteNum ASC"),
        @NamedQuery(name = "Site.selectSiteById", query = "SELECT si FROM SiteEntity si WHERE si.idSite = :idSite"),
        @NamedQuery(name = "Site.selectSiteByNum", query = "SELECT si FROM SiteEntity si WHERE si.siteNum = :siteNum"),
        @NamedQuery(name = "Site.selectListSiteBySiteName", query = "SELECT si FROM SiteEntity si WHERE si.siteName = :siteName ORDER BY si.siteName ASC"),
        @NamedQuery(name = "Site.selectListSiteByPiName", query = "SELECT si FROM SiteEntity si WHERE si.piName = :piName"),
        @NamedQuery(name = "Site.selectListSiteByStatusTrue", query = "SELECT si FROM SiteEntity si WHERE si.siteStatus = TRUE " ),
        @NamedQuery(name = "Site.findSiteByCharacteristic", query = "SELECT si FROM SiteEntity si " +
                " where ((lower(si.siteName )like concat('%', :researchWord, '%')) or" +
                " (lower(si.piName )like concat('%', :researchWord, '%')) or " +
                " (lower(si.siteNum )like concat('%', :researchWord, '%'))) ORDER BY si.siteNum ASC"),
        @NamedQuery(name = "Site.isSiteNumExist", query = "SELECT COUNT(si) FROM SiteEntity si WHERE si.siteNum = :siteNum")

})

@Entity
@Table(name = "site", schema = "sym022")
public class SiteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_site", nullable = false)
    private int idSite;

    @Basic
    @NotNull
    @Column(name = "site_num", nullable = false)
    private int siteNum;

    @Basic
    @NotNull
    @Column(name = "site_name", nullable = false, length = 200)
    private String siteName;

    @Basic
    @NotNull
    @Column(name = "pi_name", nullable = false, length = 200)
    private String piName;

    @Basic
    @Column(name = "site_status")
    private boolean siteStatus = true;

    @OneToMany(mappedBy = "siteByIdSite")
    private List<SubjectEntity> subjectsByIdSite;

    @OneToMany(mappedBy = "siteByIdSite")
    private List<UserSiteEntity> userSitesByIdSite;

    /*--- Getters and Setters ---*/

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

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteEntity that = (SiteEntity) o;
        return idSite == that.idSite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSite);
    }
}

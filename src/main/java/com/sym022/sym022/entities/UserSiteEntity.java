package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_site", schema = "sym022", catalog = "")
public class UserSiteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user_site", nullable = false)
    private int idUserSite;
    @Basic
    @Column(name = "id_site", nullable = false)
    private int idSite;
    @Basic
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @ManyToOne
    @JoinColumn(name = "id_site", referencedColumnName = "id_site", nullable = false)
    private SiteEntity siteByIdSite;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UserEntity userByIdUser;

    public int getIdUserSite() {
        return idUserSite;
    }

    public void setIdUserSite(int idUserSite) {
        this.idUserSite = idUserSite;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSiteEntity that = (UserSiteEntity) o;
        return idUserSite == that.idUserSite && idSite == that.idSite && idUser == that.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserSite, idSite, idUser);
    }

    public SiteEntity getSiteByIdSite() {
        return siteByIdSite;
    }

    public void setSiteByIdSite(SiteEntity siteByIdSite) {
        this.siteByIdSite = siteByIdSite;
    }

    public UserEntity getUserByIdUser() {
        return userByIdUser;
    }

    public void setUserByIdUser(UserEntity userByIdUser) {
        this.userByIdUser = userByIdUser;
    }
}

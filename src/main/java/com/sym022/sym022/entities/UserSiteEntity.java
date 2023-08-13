package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "UserSite.SelectAll", query = "SELECT us FROM UserSiteEntity us Where (lower(us.userByIdUser.username  )like concat('%', :researchWord, '%'))  GROUP BY us.userByIdUser.username"),
        @NamedQuery(name = "UserSite.SelectListSitesByIdUser", query = "SELECT us FROM UserSiteEntity us WHERE us.userByIdUser.idUser = :idUser"),
        @NamedQuery(name = "UserSite.SelectListSitesByIdUserANdByIdUser", query = "SELECT us FROM UserSiteEntity us WHERE us.userByIdUser.idUser = :idUser AND us.siteByIdSite.idSite = :idSite")
})

@Entity
@Table(name = "user_site", schema = "sym022")
public class UserSiteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user_site", nullable = false)
    private int idUserSite;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_site", referencedColumnName = "id_site", nullable = false)
    private SiteEntity siteByIdSite;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UserEntity userByIdUser;

    /*--- Getters and Setters ---*/

    public int getIdUserSite() {
        return idUserSite;
    }

    public void setIdUserSite(int idUserSite) {
        this.idUserSite = idUserSite;
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

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSiteEntity that = (UserSiteEntity) o;
        return idUserSite == that.idUserSite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserSite);
    }
}

package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "sym022")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @NotNull
    @Column(name = "username", nullable = false, length = 200)
    private String username;

    @Basic
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$")
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Basic
    @NotNull
    @Pattern(regexp = "^[A-Z][A-z ',\\-.-éèçàâêîûôù]{1,255}$")
    @Column(name = "last_name", nullable = false, length = 200)
    private String lastName;

    @Basic
    @NotNull
    @Pattern(regexp = "^[A-Z][A-z ',\\-.-éèçàâêîûôù]{1,255}$")
    @Column(name = "first_name", nullable = false, length = 200)
    private String firstName;

    @Basic
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @Column(name = "mail", nullable = false, length = 200)
    private String mail;

    @Basic
    @NotNull
    @Column(name = "status", nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private RoleEntity roleByIdRole;

    @OneToMany(mappedBy = "userByIdUser")
    private List<AuditTrailEntity> auditTrailsByIdUser;

    @OneToMany(mappedBy = "userByIdUser")
    private List<QueryEntity> queriesByIdUser;

    @OneToMany(mappedBy = "userByIdUser")
    private List<UserSiteEntity> userSitesByIdUser;

     /*--- Getters and Setters ---*/

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<AuditTrailEntity> getAuditTrailsByIdUser() {
        return auditTrailsByIdUser;
    }

    public void setAuditTrailsByIdUser(List<AuditTrailEntity> auditTrailsByIdUser) {
        this.auditTrailsByIdUser = auditTrailsByIdUser;
    }

    public List<QueryEntity> getQueriesByIdUser() {
        return queriesByIdUser;
    }

    public void setQueriesByIdUser(List<QueryEntity> queriesByIdUser) {
        this.queriesByIdUser = queriesByIdUser;
    }

    public RoleEntity getRoleByIdRole() {
        return roleByIdRole;
    }

    public void setRoleByIdRole(RoleEntity roleByIdRole) {
        this.roleByIdRole = roleByIdRole;
    }

    public List<UserSiteEntity> getUserSitesByIdUser() {
        return userSitesByIdUser;
    }

    public void setUserSitesByIdUser(List<UserSiteEntity> userSitesByIdUser) {
        this.userSitesByIdUser = userSitesByIdUser;
    }

    public boolean isStatus() {
        return status;
    }

    /*--- Hashcode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return idUser == that.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }
}

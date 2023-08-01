package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "sym022", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @Column(name = "username", nullable = false, length = 200)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    @Basic
    @Column(name = "last_name", nullable = false, length = 200)
    private String lastName;
    @Basic
    @Column(name = "first_name", nullable = false, length = 200)
    private String firstName;
    @Basic
    @Column(name = "mail", nullable = false, length = 200)
    private String mail;
    @Basic
    @Column(name = "status", nullable = true)
    private boolean status;
    @Basic
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @OneToMany(mappedBy = "userByIdUser")
    private List<AuditTrailEntity> auditTrailsByIdUser;
    @OneToMany(mappedBy = "userByIdUser")
    private List<QueryEntity> queriesByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private RoleEntity roleByIdRole;
    @OneToMany(mappedBy = "userByIdUser")
    private List<UserSiteEntity> userSitesByIdUser;

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

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return idUser == that.idUser && idRole == that.idRole && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(mail, that.mail) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, username, password, lastName, firstName, mail, status, idRole);
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
}

package com.sym022.sym022.entities;

import com.sym022.sym022.beans.ConnectionBean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "User.selectUser", query = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.status = TRUE"),
        @NamedQuery(name = "User.isUserExist", query = "SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "User.selectUserAll", query = "SELECT u FROM UserEntity u"),
        @NamedQuery(name = "User.selectUserById", query = "SELECT u FROM UserEntity u WHERE u.idUser = :idUser"),
        @NamedQuery(name = "User.selectUserEmptySite", query = "SELECT u FROM UserEntity u WHERE (SELECT COUNT(us) FROM UserSiteEntity us WHERE (us.userByIdUser.idUser = u.idUser))=0"),
        @NamedQuery(name = "User.findUserByCharacteristic", query = "SELECT u from UserEntity u " +
                " where ((lower(u.lastName )like concat('%', :researchWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :researchWord, '%')) or " +
                " (lower(u.mail )like concat('%', :researchWord, '%')) or" +
                " (lower(u.roleByIdRole.roleLabel) like concat('%', :researchWord, '%'))) ORDER BY u.lastName ASC")

})

@Entity
@Table(name = "user", schema = "sym022")
public class UserEntity {
    /**
     * Method to associate the list of RolePermissions with the User
     */
    @Transient
    public List<RolePermissionEntity> listOfPermissions;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @NotNull
    @Pattern(regexp = "^[A-Za-z ',\\-.-éèçàâêîûôù]{2,255}$")
    @Column(name = "username", nullable = false, length = 200)
    private String username;
    @Basic
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$")
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    @Basic
    @NotNull
    @Pattern(regexp = "^\\D{2,200}$")
    @Column(name = "last_name", nullable = false, length = 200)
    private String lastName;
    @Basic
    @NotNull
    @Pattern(regexp = "^\\D{2,200}$")
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
    private boolean status = true;
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

    public void setStatus(boolean status) {
        this.status = status;
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

    @Transient
    public List<RolePermissionEntity> getListOfRolePermissions() {
        if (this.listOfPermissions == null)
            ConnectionBean.initListPermissionRole(this);
        return this.listOfPermissions;
    }

    /**
     * Method to verify user access
     * @param permissionName
     * @return boolean
     */
    @Transient
    public boolean verifyPermission(String permissionName)
    {
        return this.getListOfRolePermissions().stream()
                .filter(pe -> pe.getPermissionByIdPermission().getPermissionLabel().equals(permissionName))
                .findFirst()
                .orElse(null) != null;
    }

}

package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role", schema = "sym022", catalog = "")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;

    @Basic
    @Column(name = "role_label", nullable = true)
    private Object roleLabel;

    @OneToMany(mappedBy = "roleByIdRole")
    private List<RolePermissionEntity> rolePermissionsByIdRole;

    @OneToMany(mappedBy = "roleByIdRole")
    private List<UserEntity> usersByIdRole;

    /*--- Getters and Setters ---*/

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public Object getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(Object roleLabel) {
        this.roleLabel = roleLabel;
    }

    public void setRolePermissionsByIdRole(List<RolePermissionEntity> rolePermissionsByIdRole) {
        this.rolePermissionsByIdRole = rolePermissionsByIdRole;
    }

    public List<UserEntity> getUsersByIdRole() {
        return usersByIdRole;
    }

    public void setUsersByIdRole(List<UserEntity> usersByIdRole) {
        this.usersByIdRole = usersByIdRole;
    }

    /*--- HasCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return idRole == that.idRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole);
    }
}

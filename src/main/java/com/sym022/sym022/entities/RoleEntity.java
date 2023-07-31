package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Collection;
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
    private Collection<RolePermissionEntity> rolePermissionsByIdRole;
    @OneToMany(mappedBy = "roleByIdRole")
    private Collection<UserEntity> usersByIdRole;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return idRole == that.idRole && Objects.equals(roleLabel, that.roleLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, roleLabel);
    }

    public Collection<RolePermissionEntity> getRolePermissionsByIdRole() {
        return rolePermissionsByIdRole;
    }

    public void setRolePermissionsByIdRole(Collection<RolePermissionEntity> rolePermissionsByIdRole) {
        this.rolePermissionsByIdRole = rolePermissionsByIdRole;
    }

    public Collection<UserEntity> getUsersByIdRole() {
        return usersByIdRole;
    }

    public void setUsersByIdRole(Collection<UserEntity> usersByIdRole) {
        this.usersByIdRole = usersByIdRole;
    }
}

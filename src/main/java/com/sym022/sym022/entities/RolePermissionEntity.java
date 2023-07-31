package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_permission", schema = "sym022", catalog = "")
public class RolePermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role_permission", nullable = false)
    private int idRolePermission;
    @Basic
    @Column(name = "id_permission", nullable = false)
    private int idPermission;
    @Basic
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @ManyToOne
    @JoinColumn(name = "id_permission", referencedColumnName = "id_permission", nullable = false)
    private PermissionEntity permissionByIdPermission;
    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private RoleEntity roleByIdRole;

    public int getIdRolePermission() {
        return idRolePermission;
    }

    public void setIdRolePermission(int idRolePermission) {
        this.idRolePermission = idRolePermission;
    }

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
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
        RolePermissionEntity that = (RolePermissionEntity) o;
        return idRolePermission == that.idRolePermission && idPermission == that.idPermission && idRole == that.idRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRolePermission, idPermission, idRole);
    }

    public PermissionEntity getPermissionByIdPermission() {
        return permissionByIdPermission;
    }

    public void setPermissionByIdPermission(PermissionEntity permissionByIdPermission) {
        this.permissionByIdPermission = permissionByIdPermission;
    }

    public RoleEntity getRoleByIdRole() {
        return roleByIdRole;
    }

    public void setRoleByIdRole(RoleEntity roleByIdRole) {
        this.roleByIdRole = roleByIdRole;
    }
}

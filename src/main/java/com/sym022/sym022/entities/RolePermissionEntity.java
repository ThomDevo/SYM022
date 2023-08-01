package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "role_permission", schema = "sym022", catalog = "")
public class RolePermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role_permission", nullable = false)
    private int idRolePermission;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_permission", referencedColumnName = "id_permission", nullable = false)
    private PermissionEntity permissionByIdPermission;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private RoleEntity roleByIdRole;

    /*--- Getters and Setters ---*/

    public int getIdRolePermission() {
        return idRolePermission;
    }

    public void setIdRolePermission(int idRolePermission) {
        this.idRolePermission = idRolePermission;
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

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionEntity that = (RolePermissionEntity) o;
        return idRolePermission == that.idRolePermission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRolePermission);
    }
}

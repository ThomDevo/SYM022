package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "permission", schema = "sym022", catalog = "")
public class PermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_permission", nullable = false)
    private int idPermission;
    @Basic
    @Column(name = "permission_label", nullable = false, length = 200)
    private String permissionLabel;
    @Basic
    @Column(name = "permission_description", nullable = true, length = 200)
    private String permissionDescription;
    @OneToMany(mappedBy = "permissionByIdPermission")
    private Collection<RolePermissionEntity> rolePermissionsByIdPermission;

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    public String getPermissionLabel() {
        return permissionLabel;
    }

    public void setPermissionLabel(String permissionLabel) {
        this.permissionLabel = permissionLabel;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntity that = (PermissionEntity) o;
        return idPermission == that.idPermission && Objects.equals(permissionLabel, that.permissionLabel) && Objects.equals(permissionDescription, that.permissionDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermission, permissionLabel, permissionDescription);
    }

    public Collection<RolePermissionEntity> getRolePermissionsByIdPermission() {
        return rolePermissionsByIdPermission;
    }

    public void setRolePermissionsByIdPermission(Collection<RolePermissionEntity> rolePermissionsByIdPermission) {
        this.rolePermissionsByIdPermission = rolePermissionsByIdPermission;
    }
}

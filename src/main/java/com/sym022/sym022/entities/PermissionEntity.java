package com.sym022.sym022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Permission.selectPermissionAll", query = "SELECT p from PermissionEntity p order by p.idPermission desc"),
        @NamedQuery(name = "Permission.selectPermissionById", query = "SELECT p from PermissionEntity p where p.idPermission = :idPermission"),
        @NamedQuery(name = "Permission.selectPermissionByLabel", query = "SELECT p from PermissionEntity p where p.permissionLabel = :permissionLabel"),

})

@Entity
@Table(name = "permission", schema = "sym022")
public class PermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_permission", nullable = false)
    private int idPermission;

    @Basic
    @NotNull
    @Column(name = "permission_label", nullable = false, length = 200)
    private String permissionLabel;

    @Basic
    @Column(name = "permission_description", nullable = true, length = 200)
    private String permissionDescription;

    @OneToMany(mappedBy = "permissionByIdPermission")
    private List<RolePermissionEntity> rolePermissionsByIdPermission;

    /*--- Getters and Setters ---*/

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

    public List<RolePermissionEntity> getRolePermissionsByIdPermission() {
        return rolePermissionsByIdPermission;
    }

    public void setRolePermissionsByIdPermission(List<RolePermissionEntity> rolePermissionsByIdPermission) {
        this.rolePermissionsByIdPermission = rolePermissionsByIdPermission;
    }

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntity that = (PermissionEntity) o;
        return idPermission == that.idPermission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermission);
    }
}

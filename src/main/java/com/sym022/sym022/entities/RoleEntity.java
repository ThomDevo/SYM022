package com.sym022.sym022.entities;

import com.sym022.sym022.enums.RoleLabel;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Role.SelectRoleByRoleName", query = "SELECT r from RoleEntity r where r.roleLabel = :roleLabel"),
        @NamedQuery(name="Role.SelectRoleAll", query ="SELECT r from RoleEntity r"),
        @NamedQuery(name="Role.SelectRoleAllEmpty", query ="select r from RoleEntity r where (select count(rp) from RolePermissionEntity rp where (rp.roleByIdRole.idRole = r.idRole) ) = 0"),
        @NamedQuery(name="Role.SelectRoleById", query="SELECT r from RoleEntity r where r.idRole = :idRole"),
        @NamedQuery(name="Role.isRoleExist", query="SELECT COUNT(r) FROM RoleEntity r WHERE r.roleLabel = :roleLabel"),
        @NamedQuery(name="Role.SelectAllRoleFilter", query="SELECT r FROM RoleEntity r WHERE ((lower(r.roleLabel) LIKE CONCAT('%', :researchRole, '%'))) ORDER BY r.roleLabel ASC")
})


@Entity
@Table(name = "role", schema = "sym022")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;

    @Basic
    @NotNull
    @Column(name = "role_label", nullable = false)
    private RoleLabel roleLabel;

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

    public RoleLabel getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(RoleLabel roleLabel) {
        this.roleLabel = roleLabel;
    }

    public List<RolePermissionEntity> getRolePermissionsByIdRole() {
        return rolePermissionsByIdRole;
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

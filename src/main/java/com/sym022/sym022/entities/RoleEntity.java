package com.sym022.sym022.entities;

import com.sym022.sym022.enums.RoleLabel;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Role.selectRoleByRoleLabel", query = "SELECT r from RoleEntity r where r.roleLabel = :roleLabel"),
        @NamedQuery(name="Role.selectRoleAll", query ="SELECT r from RoleEntity r"),
        @NamedQuery(name="Role.selectRoleAllEmpty", query ="select r from RoleEntity r where (select count(rp) from RolePermissionEntity rp where (rp.roleByIdRole.idRole = r.idRole) ) = 0"),
        @NamedQuery(name="Role.selectRoleById", query="SELECT r from RoleEntity r where r.idRole = :idRole"),
        @NamedQuery(name="Role.isRoleExist", query="SELECT COUNT(r) FROM RoleEntity r WHERE r.roleLabel = :roleLabel"),
        @NamedQuery(name="Role.selectAllRoleFilter", query="SELECT r FROM RoleEntity r WHERE ((lower(r.roleLabel) LIKE CONCAT('%', :researchWord, '%'))) ORDER BY r.roleLabel ASC")
})


@Entity
@Table(name = "role", schema = "sym022")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;

    @Basic
    @Column(name = "role_label", nullable = false, length = 200)
    private String roleLabel;

    @OneToMany(mappedBy = "roleByIdRole")
    private List<RolePermissionEntity> rolePermissionsByIdRole;

    @OneToMany(mappedBy = "roleByIdRole")
    private List<UserEntity> usersByIdRole;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    /*--- Getters and Setters ---*/

    public String getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(String roleLabel) {
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
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole);
    }
}

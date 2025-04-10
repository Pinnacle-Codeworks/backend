package com.markguiang.backend.auth.role;

import com.markguiang.backend.auth.config.enum_.RoleType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long roleId;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    public RoleType roleType;

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        final Role other = (Role) obj;
        return Objects.equals(this.roleType, other.roleType);
    }
}
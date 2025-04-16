package com.markguiang.backend.user;

import com.markguiang.backend.auth.role.Role;
import com.markguiang.backend.form.model.FormAnswers;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // foreignKeys
    private Long tenantId;

    // mappings
    @ManyToMany
    private List<Role> roles;

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private Set<FormAnswers> formAnswersSet;

    // fields
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<FormAnswers> getFormAnswersSet() {
        return formAnswersSet;
    }

    public void setFormAnswersSet(Set<FormAnswers> formAnswersSet) {
        this.formAnswersSet = formAnswersSet;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

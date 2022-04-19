package com.youtube.jwt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Role {

//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer role_id;
    @Id
    private String roleName;
    private String roleDescription;
//    @ManyToMany(mappedBy = "role")
//    private Set<User> user;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

//    public Integer getRole_id() {
//        return role_id;
//    }

//    public Set<User> getUser() {
//        return user;
//    }

//    public void setRole_id(Integer role_id) {
//        this.role_id = role_id;
//    }

//    public void setUser(Set<User> user) {
//        this.user = user;
//    }
    public void setRoleDescription(String roleDescription)
    {
        this.roleDescription = roleDescription;
    }
}

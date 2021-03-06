package com.youtube.jwt.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
    @Id
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ApplyJob> applyJobs;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    public String getUserPassword() {
//        return userPassword;
//    }
}

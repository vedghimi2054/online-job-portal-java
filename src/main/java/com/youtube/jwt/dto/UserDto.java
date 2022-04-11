package com.youtube.jwt.dto;

import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.entity.Role;
import lombok.Data;

import javax.persistence.ManyToMany;
import java.util.Set;

@Data
public class UserDto {
    private String userName;
    private String userFirstName;
    private String userPassword;
    private String userEmail;

    @ManyToMany
    private Set<Role> roles;

    @ManyToMany
    private Set<ApplyJob> applyJobs;
}

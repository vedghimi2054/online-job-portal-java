package com.youtube.jwt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
@Setter
@Getter
@Entity
public class Role {
    @Id
    private String roleName;
    private String roleDescription;
}

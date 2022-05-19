package com.youtube.jwt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "apply_job")
public class ApplyJob {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Integer job_id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String skills;
    private String resume;
}

package com.youtube.jwt.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="apply_job")
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

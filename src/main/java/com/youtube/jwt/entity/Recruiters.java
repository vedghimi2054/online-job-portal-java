package com.youtube.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Recruiters {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Integer company_id;
    private String companyName;
    private String companyLogo;
    private String phoneNumber;
    private String websiteUrl;

    @ManyToMany
    public Set<JobPosts> jobPosts;
}

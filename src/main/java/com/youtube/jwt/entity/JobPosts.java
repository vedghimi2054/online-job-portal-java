package com.youtube.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class JobPosts {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Integer postId;
    private String companyName;
    private String jobTitle;
    private String description;
    private LocalDate postDate;
    private LocalDate lastDate;
    @Enumerated(EnumType.STRING)
    private PostStatus status;


}

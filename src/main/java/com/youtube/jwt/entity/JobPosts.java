package com.youtube.jwt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String userName;
    private String companyName;
    private String jobTitle;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate postDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastDate;
    @Enumerated(EnumType.STRING)
    private PostStatus status;


}

package com.youtube.jwt.dto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private String firstName=" ";

    private String middleName = " ";

    private String lastName = " ";

    private String email = " ";

    private String description = " ";

}

package com.project.Investo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {

    private String name;
    private String email;
    private String mobileNumber;
    private boolean mobileVerified;
}
package com.github.bruce_mig.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private CardDetails cardDetails;
}

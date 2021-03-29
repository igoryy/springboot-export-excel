package com.example.demo.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {


    private Integer id;

    private String email;

    private String password;

    private String fullName;

    private boolean enabled;

}

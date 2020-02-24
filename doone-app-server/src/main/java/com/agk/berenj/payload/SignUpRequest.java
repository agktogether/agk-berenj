package com.agk.berenj.payload;

import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

public class SignUpRequest {
    @Nullable
    @Size(min = 0, max = 40)
    private String name;

    @NotBlank
    @Size(min = 11, max = 11)
    private String username;

    @Nullable
    @Size(min = 6, max = 20)
    private String password;
//
//    @NotBlank
//    @Size(max = 40)
//    @Email
//    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

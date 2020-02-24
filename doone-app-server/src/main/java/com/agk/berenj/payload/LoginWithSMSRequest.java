package com.agk.berenj.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@AllArgsConstructor
@Getter
public class LoginWithSMSRequest {

    @NotBlank
    private String phonenumber;
    @NotBlank
    private String code;

}

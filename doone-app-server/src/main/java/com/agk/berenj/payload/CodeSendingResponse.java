package com.agk.berenj.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CodeSendingResponse {

    private Long smsreqid;

    private String phoneNumber;
    private int status;
}

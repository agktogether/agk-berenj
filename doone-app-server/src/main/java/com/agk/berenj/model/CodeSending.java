package com.agk.berenj.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class CodeSending {
    public static int SENT = 1;
    public static int NOTSENTYET = 0;

    @Id
    private String phoneNumber;
    private int status;
    private String code;

    public CodeSending(String phoneNumber, String code, int status) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.status = status;
    }

    public CodeSending() {
    }
}

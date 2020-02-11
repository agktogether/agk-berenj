package com.agk.berenj.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class VerifyingOrderResponse {
    private int payablePrice;
    private String factorNumber;
    private long issuedFactorTime;
    private PaymentType paymentType;

    public VerifyingOrderResponse(int payablePrice, String factorNumber, long issuedFactorTime, PaymentType paymentType) {
        this.payablePrice = payablePrice;
        this.factorNumber = factorNumber;
        this.issuedFactorTime = issuedFactorTime;
        this.paymentType = paymentType;
    }
}

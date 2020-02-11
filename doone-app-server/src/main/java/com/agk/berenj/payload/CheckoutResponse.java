package com.agk.berenj.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CheckoutResponse {
    private UserCheckout user;

}

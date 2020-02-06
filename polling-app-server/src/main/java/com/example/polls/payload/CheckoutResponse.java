package com.example.polls.payload;

import com.example.polls.model.Order;
import com.example.polls.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Access;

@Getter
@Setter
@Accessors(chain = true)
public class CheckoutResponse {
    private UserCheckout user;

}

package com.example.polls.payload;

import com.example.polls.model.Address;
import com.example.polls.model.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderRequest {

    private UserCheckout userCheckout;
    private Order order;
    private long orderDeliverTime;
    private PersianDate orderDeliverPersianDate;
    private Address deliveringAddress;

}

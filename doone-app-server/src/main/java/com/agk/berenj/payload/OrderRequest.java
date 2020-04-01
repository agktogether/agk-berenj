package com.agk.berenj.payload;

import com.agk.berenj.model.Address;
import com.agk.berenj.model.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderRequest {

    private UserInfo userInfo;
    private Order order;
    private long orderDeliverTime;
    private PersianDate orderDeliverPersianDate;
    private Address deliveringAddress;

}

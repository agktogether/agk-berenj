package com.agk.berenj.payload;

import com.agk.berenj.model.Address;
import com.agk.berenj.model.ClientsideOrder;
import com.agk.berenj.model.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderRequest {

    private OtherUserInfo otherUserInfo;
    private ClientsideOrder clientsideOrder;
    private long orderDeliverTime;
    private PersianDate orderDeliverPersianDate;
    private long deliveringAddressId;
    private String couponCode;
    private int paymentType=0;


}

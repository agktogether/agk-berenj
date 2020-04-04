package com.agk.berenj.model;

import com.agk.berenj.payload.PersianDate;
import com.agk.berenj.payload.ProductApplied;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Order {

    @Id
    @JsonIgnore
    private String _id;
    private Long userId;
    private long orderTime;
    private List<ProductApplied> products;
    private OrderStatus orderStatus;

    private long orderDeliverUnixTime;
    private PersianDate orderDeliverPersianDate;
    private int paymentType;

    public Order() {
    }
}

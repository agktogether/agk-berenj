package com.example.polls.model;

import com.example.polls.payload.ProductApplied;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Getter
public class Order {
    private Long id;
    private List<ProductApplied> products;
    private Integer totalPrice;

}

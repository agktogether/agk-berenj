package com.example.polls.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class Order {
    private Long id;
    private List<Product> products;
}

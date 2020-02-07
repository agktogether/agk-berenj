package com.example.polls.model;

import com.example.polls.payload.ProductApplied;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class OrderId {

    @Id
    private String _id;

}

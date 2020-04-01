package com.agk.berenj.model;

import com.agk.berenj.payload.ProductApplied;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ClientsideOrder {
    private List<ProductApplied> products;
     private String description;
}

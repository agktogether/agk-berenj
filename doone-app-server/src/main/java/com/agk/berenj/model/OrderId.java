package com.agk.berenj.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Accessors(chain = true)
public class OrderId {

    @Id
    private String _id;

}

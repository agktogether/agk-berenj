package com.example.polls.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductApplied {
    @Id
    private Long id;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    @Size(max = 140)
    private String name;

    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer price;

    @NotBlank
    @Size(min = 1)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;



}

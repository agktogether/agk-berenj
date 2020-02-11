package com.agk.berenj.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
@Getter
public class Product {
    @Id
    private Long id;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    @Size(max = 140)
    private String name;

    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer price;


}

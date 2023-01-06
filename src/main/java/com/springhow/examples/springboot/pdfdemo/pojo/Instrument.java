package com.springhow.examples.springboot.pdfdemo.pojo;

import lombok.Data;

import java.math.BigDecimal;

/* carlpeters created on 06/01/2023 inside the package - com.springhow.examples.springboot.pdfdemo.pojo */
@Data
public class Instrument {

    private String isinCode;
    private String name;
    private String shortName;
    private String market;
    private Integer quantity;
    private BigDecimal highest;
    private BigDecimal lowest;
    private BigDecimal currentPrice;

}

package com.geek.gmq.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author itguoy
 * @date 2021-12-15 15:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private Long ts;

    private String symbol;

    private Double price;
}

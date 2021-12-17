package com.geek.gmq.common.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author itguoy
 * @date 2021-12-16 16:46
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    private Long ts;

    private String symbol;

    private Double price;
}

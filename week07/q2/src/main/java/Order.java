package week07;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author itguoy
 * @date 2021-11-06 16:05
 */

@Data
@Accessors(chain = true)
public class Order {


    private long order_id;

    private long user_id;

    private long prod_id;

    private BigDecimal order_price;

    private int order_num;

    private BigDecimal order_total_price;

    private int order_status;


}

package com.bruce;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rcy
 * @version 1.1.0
 * @className: Order
 * @date 2022-05-13
 */
@Data
public class Order implements Serializable {

    private String orderNo;

    private String orderMsg;

}

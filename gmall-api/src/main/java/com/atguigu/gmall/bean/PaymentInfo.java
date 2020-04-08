package com.atguigu.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @param
 * @return
 */
@Data
public class PaymentInfo implements Serializable {

    @Column
    @Id
    private String  id;

    @Column
    private String orderSn;

    @Column
    private String orderId;

    @Column
    private String alipayTradeNo;

    @Column
    private BigDecimal totalAmount;

    @Column
    private String subject;

    @Column
    private String paymentStatus;

    @Column
    private Date createTime;

    @Column
    private Date callbackTime;

    @Column
    private String callbackContent;

}

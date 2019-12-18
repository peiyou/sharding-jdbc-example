package com.xintanyun.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


@Getter
@Setter
public class Trade implements Serializable {

    @Id
    private Long id;

    private Long userId;

    private BigInteger amount;

    private Integer status;

    private Long transactionId;

    private Date createdTime;

    private Date updatedTime;
}

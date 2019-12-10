package com.xintanyun.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
public class Transaction implements Serializable {

    private Long id;

    private Long userId;

    private BigInteger amount;

    private Date createdTime;
}

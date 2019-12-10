package com.xintanyun.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private BigInteger asset;

    private String name;

    private Date createdTime;
}

package com.xintanyun.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class TableId implements Serializable {

    @Id
    private String tableName;

    private Long currentId;

    private Long stepValue;

    private Date createdTime;

    private Date updatedTime;
}

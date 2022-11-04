package com.zime.ojdemo.modle.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageList implements Serializable {
    long total;
    List rows;
}

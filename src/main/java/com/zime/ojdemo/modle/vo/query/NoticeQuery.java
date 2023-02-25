package com.zime.ojdemo.modle.vo.query;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeQuery {

    String title;
    Date createTime;
    Boolean state;
    Boolean isSetTop;

}

package com.zime.ojdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Work {
   @TableId
   private Integer workid;
   private String name;
   private String probelmid;
   private Long starttime;
   private Long endtime;

   private String type;
   private String founder;
   private String workpassword;
   private Boolean open;
   private String notice;
}

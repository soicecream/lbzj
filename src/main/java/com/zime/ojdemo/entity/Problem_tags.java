package com.zime.ojdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Problem_tags {

    private static final long serialVersionUID=1L;

    @TableId(value = "problem_id", type = IdType.AUTO)
    private Integer problemId;

    @TableId(value = "tags_id", type = IdType.AUTO)
    private String tagsId;

}

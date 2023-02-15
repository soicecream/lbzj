package com.zime.ojdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2023-02-14
 */
@Getter
@Setter
  @TableName("sys_problem_case")
public class ProblemCase implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer problemId;

      private String input;

      private String output;


}

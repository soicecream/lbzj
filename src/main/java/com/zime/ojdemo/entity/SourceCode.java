package com.zime.ojdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zime
 * @since 2021-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SourceCode implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "solution_id", type = IdType.AUTO)
    private Integer solutionId;

    private String source;

}

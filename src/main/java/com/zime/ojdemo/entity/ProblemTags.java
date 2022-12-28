package com.zime.ojdemo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2022-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProblemTags implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer problemId;

    private Integer tagsId;


}

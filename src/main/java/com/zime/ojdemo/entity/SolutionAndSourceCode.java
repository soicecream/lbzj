package com.zime.ojdemo.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class SolutionAndSourceCode implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer solutionId;

    private Integer problemId;

    private String userId;

    private String nick;

    private Integer time;

    private Integer memory;

    private String inDate;

    private Integer result;

    private Integer language;

    private String ip;

    private Integer contestId;

    private Integer valid;

    private Integer num;

    private Integer codeLength;

    private Date judgetime;

    private BigDecimal passRate;

    private Integer lintError;

    private String judger;

    private Integer courseId;

    private String source;


}

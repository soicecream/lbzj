package com.zime.ojdemo.modle.vo;

import com.zime.ojdemo.entity.Problem;
import lombok.Data;

@Data
public class AddProblem extends Problem {
    private String dataInput;
    private String dataOutput;
}

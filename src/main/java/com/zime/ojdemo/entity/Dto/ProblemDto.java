package com.zime.ojdemo.entity.Dto;

import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.entity.ProblemCase;
import com.zime.ojdemo.entity.Tags;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProblemDto {

    private Problem problem;

    private List<Tags> tagsList;

}

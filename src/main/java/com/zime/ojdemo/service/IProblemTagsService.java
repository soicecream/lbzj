package com.zime.ojdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.entity.ProblemTags;
import com.zime.ojdemo.entity.Tags;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-12-21
 */
public interface IProblemTagsService extends IService<ProblemTags> {

    Boolean CreateOrUpdate(Integer problemId, List<Integer> tags);

    List<Tags> getProblemTags(Integer problemId);
}

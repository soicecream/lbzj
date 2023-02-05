package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.entity.ProblemTags;
import com.zime.ojdemo.entity.Tags;
import com.zime.ojdemo.mapper.ProblemTagsMapper;
import com.zime.ojdemo.service.IProblemTagsService;
import com.zime.ojdemo.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-12-21
 */
@Service
public class ProblemTagsServiceImpl extends ServiceImpl<ProblemTagsMapper, ProblemTags> implements IProblemTagsService {

    @Autowired
    private TagsService tagsService;

    @Override
    public List<Tags> getProblemTags(Integer problemId) {
        QueryWrapper<ProblemTags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", problemId);
        List<ProblemTags> problemTagsList = list(queryWrapper);

        List<Tags> All = tagsService.list();
        TreeMap<Integer, Tags> treeMap = new TreeMap<>();
        for (Tags i : All) {
            treeMap.put(i.getId(), i);
        }

        List<Tags> tagsList = new ArrayList<>();
        for (ProblemTags i : problemTagsList) {
            tagsList.add(treeMap.get(i.getTagsId()));
        }
        return tagsList;
    }

    @Override
    public Boolean CreateOrUpdate(Integer problemId, List<Integer> tags) {
        QueryWrapper<ProblemTags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", problemId);
        remove(queryWrapper);

        List<ProblemTags> list = new ArrayList<>();
        ProblemTags problemTags = new ProblemTags();
        problemTags.setProblemId(problemId);
        for (int i : tags) {
            problemTags.setTagsId(i);
            list.add(problemTags);
        }

        return saveOrUpdateBatch(list);
    }


}

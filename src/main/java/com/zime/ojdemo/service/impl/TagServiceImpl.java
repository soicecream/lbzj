package com.zime.ojdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.entity.Tags;
import com.zime.ojdemo.mapper.TagsMapper;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.service.TagsService;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Override
    public Boolean CreateOrUpdate(Tags tags) {
        if (tags.getId() == null) {
            return save(tags);
        } else {
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", tags.getId());
            return update(tags, queryWrapper);
        }
    }

}

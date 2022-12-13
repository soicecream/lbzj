package com.zime.ojdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.entity.Tags;
import com.zime.ojdemo.mapper.TagsMapper;
import com.zime.ojdemo.service.TagsService;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

}

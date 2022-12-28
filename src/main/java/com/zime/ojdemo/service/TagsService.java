package com.zime.ojdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.entity.Tags;

public interface TagsService extends IService<Tags> {
    Boolean CreateOrUpdate(Tags tags);
}

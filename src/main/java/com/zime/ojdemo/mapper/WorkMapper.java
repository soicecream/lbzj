package com.zime.ojdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zime.ojdemo.entity.Work;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
public interface WorkMapper extends BaseMapper<Work> {
}

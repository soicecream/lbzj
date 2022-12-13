package com.zime.ojdemo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zime.ojdemo.entity.Tags;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("allTags")
    public JsonResult AllTags() {
        return JsonResult.success(tagsService.list());
    }

    @PostMapping("createOrUpdate")
    public JsonResult CreateOrUpdate(@RequestBody Tags tags) {
        if (tags.getId() == null) {
            return JsonResult.success(tagsService.save(tags));
        } else {
            QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", tags.getId());
            return JsonResult.success(tagsService.update(tags, queryWrapper));
        }
    }

}

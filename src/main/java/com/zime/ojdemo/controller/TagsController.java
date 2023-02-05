package com.zime.ojdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Tags;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.query.TagsQuery;
import com.zime.ojdemo.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("allTags")
    public JsonResult AllTags() {
        return JsonResult.success(tagsService.list());
    }

    @PostMapping("get/page/{current}/{limit}")
    public JsonResult GetPage(@PathVariable long current, @PathVariable long limit, @RequestBody TagsQuery tagsQuery) {
        System.out.println("-------------------------------------------");
        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
        String value = tagsQuery.getValue();
        if(!value.equals("")) queryWrapper.like("value", value);
        System.out.println("---------------" + value);
        return JsonResult.success(tagsService.page(new Page<>(current, limit), queryWrapper));
    }

    @PostMapping("createOrUpdate")
    public JsonResult CreateOrUpdate(@RequestBody Tags tags) {
        if (tags.getValue() == null) {
            return JsonResult.error(405, "请输入标签名");
        }

        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("value", tags.getValue());
        List<Tags> list = tagsService.list(queryWrapper);
        if (list.size() != 0 && !(list.size() == 1 && list.get(0).getId() == tags.getId())) {
            return JsonResult.error(400, "标签名已存在");
        }

        return JsonResult.success(tagsService.CreateOrUpdate(tags));
    }

    @PostMapping("deleteIds")
    public JsonResult MyDeleteIds(@RequestBody ArrayList<Integer> ids) {
        return JsonResult.success(tagsService.removeBatchByIds(ids));
    }

}

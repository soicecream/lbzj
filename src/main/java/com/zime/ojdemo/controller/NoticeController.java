package com.zime.ojdemo.controller;


import com.zime.ojdemo.entity.Notice;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.query.NoticeQuery;
import com.zime.ojdemo.service.INoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private INoticeService noticeService;

    @PostMapping("/createOrUpdate")
    private JsonResult createOrUpdate(@RequestBody Notice notice) {
        return noticeService.createOrUpdate(notice);
    }

    @PostMapping("/admin/get/page/{current}/{limit}")
    private JsonResult getPage(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody(required = false) NoticeQuery noticeQuery) {
        return JsonResult.success(noticeService.adminGETPage(current, limit, noticeQuery));
    }

    @PostMapping("/delete/ids")
    public JsonResult deleteByIds(@RequestBody ArrayList<Integer> ids) {
        return JsonResult.success(noticeService.deleteByIds(ids));
    }

    @PostMapping("/get/home")
    public JsonResult getHome() {
        return noticeService.GETHOME();
    }

    @PostMapping("/get/{noticeId}")
    public JsonResult getInformation(@PathVariable Integer noticeId) {
        return noticeService.getInformation(noticeId);
    }

    @PostMapping("/get/page/list/{current}/{limit}")
    public JsonResult getPageList(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody NoticeQuery noticeQuery) {
        return noticeService.getPageList(current, limit, noticeQuery);
    }
}


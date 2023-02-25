package com.zime.ojdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Dto.Notice.NOTICELIST;
import com.zime.ojdemo.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.query.NoticeQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-02-18
 */
public interface INoticeService extends IService<Notice> {

    Page<Notice> adminGETPage(Integer current, Integer limit, NoticeQuery noticeQuery);

    JsonResult createOrUpdate(Notice notice);

    Boolean deleteByIds(ArrayList<Integer> ids);

    JsonResult GETHOME();

    JsonResult getInformation(Integer noticeId);

    JsonResult getPageList(Integer current, Integer limit, NoticeQuery noticeQuery);
}

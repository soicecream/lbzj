package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Dto.Notice.NOTICELIST;
import com.zime.ojdemo.entity.Notice;
import com.zime.ojdemo.entity.Users;
import com.zime.ojdemo.mapper.NoticeMapper;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import com.zime.ojdemo.modle.vo.query.NoticeQuery;
import com.zime.ojdemo.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.service.UsersService;
import com.zime.ojdemo.untils.SecurityUntils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2023-02-18
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Resource
    public UsersService usersService;

    @Override
    public JsonResult createOrUpdate(Notice notice) {
        LoginUser user = SecurityUntils.getLoginUser();
        List<Notice> list = getByTitleList(notice.getTitle());
        if (notice.getId() == null) {
            if (list.size() != 0) {
                return JsonResult.error(400, "公告标题已重复");
            }
            notice.setCreatorId(user.getUserId());
            notice.setCreateTime(new Date());
            notice.setUpdatorId(user.getUserId());
            notice.setUpdateTime(new Date());
            return JsonResult.success(save(notice));
        } else {
            if (list.size() > 0 && list.get(0).getId() != notice.getId()) {
                return JsonResult.error(400, "公告标题已重复");
            }
            notice.setUpdatorId(user.getUserId());
            notice.setUpdateTime(new Date());
            return JsonResult.success(update(notice, new QueryWrapper<Notice>().eq("id", notice.getId())));
        }
    }

    @Override
    public Boolean deleteByIds(ArrayList<Integer> ids) {
        return removeByIds(ids);
    }

    @Override
    public JsonResult GETHOME() {
        Page<Notice> noticePage = setListUsername(page(new Page<>(1, 5), new QueryWrapper<Notice>().orderByDesc("create_time").eq("state", 1)));

        List<NOTICELIST> list = new ArrayList<>();
        for (int i = 0; i < noticePage.getTotal(); i++) {
            Notice notice = noticePage.getRecords().get(i);
            NOTICELIST homeNotice = new NOTICELIST();
            BeanUtils.copyProperties(notice, homeNotice);
            list.add(homeNotice);
        }

        return JsonResult.success(list);
    }

    @Override
    public JsonResult getInformation(Integer noticeId) {
        Notice notice = getOne(new QueryWrapper<Notice>().eq("id", noticeId));
        if (notice == null || !notice.getState()) {
            return JsonResult.error(400, "获取失败");
        }

        notice.setCreator(userIdGetUsername(notice.getCreatorId()));
        return JsonResult.success(notice);
    }

    @Override
    public JsonResult getPageList(Integer current, Integer limit, NoticeQuery noticeQuery) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>().orderByDesc("is_set_top").orderByDesc("create_time").eq("state", 1);

        if (noticeQuery.getTitle() != null || !noticeQuery.equals("")) {
            queryWrapper.like("title", noticeQuery.getTitle());
        }
        Page<Notice> page = setListUsername(page(new Page<>(current, limit), queryWrapper));

        List<NOTICELIST> list = new ArrayList<>();
        for (int i = 0; i < page.getTotal(); i++) {
            Notice notice = page.getRecords().get(i);
            NOTICELIST getListNotice = new NOTICELIST();
            BeanUtils.copyProperties(notice, getListNotice);
            list.add(getListNotice);
        }
        return JsonResult.success(list);
    }

    @Override
    public Page<Notice> adminGETPage(Integer current, Integer limit, NoticeQuery noticeQuery) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");

        //判断条件值是否为空，如果不为空拼接条件
        if (noticeQuery.getTitle() != null) {
            queryWrapper.like("title", noticeQuery.getTitle());
        }
        if (noticeQuery.getState() != null) {
            queryWrapper.eq("state", noticeQuery.getState());
        }
        if (noticeQuery.getIsSetTop() != null) {
            queryWrapper.eq("is_set_top", noticeQuery.getIsSetTop());
        }

        return setListUsername(page(new Page<>(current, limit), queryWrapper));
    }

    //    ----------------------------------------------------------------------------------------------------------------------------------


    //    ----------------------------------------------------------------------------------------------------------------------------------

    //    设置用户名
    public Page<Notice> setListUsername(Page<Notice> page) {
        for (int i = 0; i < page.getTotal(); i++) {
            Notice j = page.getRecords().get(i);
            Long creatorId = j.getCreatorId(), updatorId = j.getUpdatorId();
            String creator = userIdGetUsername(creatorId), updator = userIdGetUsername(updatorId);
            page.getRecords().get(i).setCreator(creator);
            page.getRecords().get(i).setUpdator(updator);
        }
        return page;
    }

    //    标题查询所有
    public List<Notice> getByTitleList(String title) {
        return list(new QueryWrapper<Notice>().eq("title", title));
    }

    //    标题查询一个
    public Notice getByTitleOne(String title) {
        return getOne(new QueryWrapper<Notice>().eq("title", title));
    }

    //    id获取用户名
    public String userIdGetUsername(long userId) {
        return usersService.getOne(new QueryWrapper<Users>().eq("user_id", userId)).getUserName();
    }


}

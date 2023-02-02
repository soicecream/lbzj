package com.zime.ojdemo.mapper;

import com.zime.ojdemo.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.VueUserRole;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<VueUserRole> pageUserRoles(@Param("start")Long start, @Param("end") Long end,
                                    @Param("rolename")String rolename,@Param("username") String username);
}

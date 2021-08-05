package com.yusys.agile.projectmanager.service.impl;

import com.github.pagehelper.PageHelper;
import com.yusys.agile.projectmanager.dao.SProjectManagerMapper;
import com.yusys.agile.projectmanager.domain.SProjectManager;
import com.yusys.agile.projectmanager.service.SProjectManagerServcice;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SProjectManagerServciceImpl
 * @Description
 * @Author wangpf6
 * @Date 2021/8/3 17:42
 * @Version 1.0
 **/
public class SProjectManagerServciceImpl implements SProjectManagerServcice {

    @Resource
    private SProjectManagerMapper sProjectManagerMapper;

    /**
     * @return: java.util.List<com.yusys.agile.projectmanager.domain.SProjectManager>
     * @Author wangpf6
     * @Description 根据项目名称/项目代号和需求标题查询项目列表
     * @Date 18:55 2021/8/3
     * @Param [sprojectName, pageNum, pageSize, securityDTO, demandTitle]
     **/
    @Override
    public List<SProjectManager> listSProjectByName(String sprojectName, Integer pageNum, Integer pageSize, SecurityDTO securityDTO, String demandTitle) {
        //不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
//        List<SProjectManager> sProjectManagers=sProjectManagerMapper.
        return null;
    }
}

package com.yusys.agile.projectmanager.service;

import com.yusys.agile.projectmanager.domain.SProjectManager;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;

import java.util.List;

/**
 * @ClassName SProjectManagerServcice
 * @Description
 * @Author wangpf6
 * @Date 2021/8/3 18:01
 * @Version 1.0
 **/
public interface SProjectManagerServcice {
    List<SProjectManager> listSProjectByName(String sprojectName, Integer pageNum, Integer pageSize, SecurityDTO securityDTO, String demandTitle);

}

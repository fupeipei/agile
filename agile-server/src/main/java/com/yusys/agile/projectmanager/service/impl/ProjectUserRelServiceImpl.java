package com.yusys.agile.projectmanager.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import com.yusys.agile.projectmanager.dao.SProjectManagerMapper;
import com.yusys.agile.projectmanager.dao.SProjectUserRelMapper;
import com.yusys.agile.projectmanager.domain.SProjectManagerExample;
import com.yusys.agile.projectmanager.domain.SProjectUserRel;
import com.yusys.agile.projectmanager.domain.SProjectUserRelExample;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.agile.projectmanager.service.ProjectUserRelService;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.PageInfoDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectUserRelServiceImpl implements ProjectUserRelService {


    @Autowired
    private SProjectUserRelMapper sProjectUserRelMapper;


    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    @Override
    public PageInfo<SsoUserDTO> queryUserInfoByCondition(Integer pageNum, Integer pageSize, String searchKey, Long projectId) {
        PageInfo<SsoUserDTO> resutl = new PageInfo<>();
        SProjectUserRelExample sProjectUserRelExample = new SProjectUserRelExample();
        sProjectUserRelExample.createCriteria().andProjectIdEqualTo(projectId)
                .andStateEqualTo(StateEnum.U.getValue());
        List<SProjectUserRel> sProjectUserRels = sProjectUserRelMapper.selectByExample(sProjectUserRelExample);
        if (CollectionUtils.isNotEmpty(sProjectUserRels)){
            List<Long> userIds = sProjectUserRels.stream().map(SProjectUserRel::getUserId).collect(Collectors.toList());
            PageInfoDTO<SsoUserDTO> ssoUserDTOPageInfoDTO = iFacadeUserApi.queryUserInfoByCondition(userIds, searchKey, pageNum, pageSize);
            resutl.setList(ssoUserDTOPageInfoDTO.getList());
            resutl.setSize(ssoUserDTOPageInfoDTO.getSize());
            resutl.setPageNum(pageNum);
            resutl.setPageSize(pageSize);
            resutl.setTotal(ssoUserDTOPageInfoDTO.getTotal());
        }
        return resutl;
    }
}

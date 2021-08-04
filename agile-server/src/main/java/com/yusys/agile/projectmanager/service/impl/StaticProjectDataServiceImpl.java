package com.yusys.agile.projectmanager.service.impl;

import com.yusys.agile.projectmanager.dao.SStaticProjectDataMapper;
import com.yusys.agile.projectmanager.domain.SStaticProjectData;
import com.yusys.agile.projectmanager.domain.SStaticProjectDataExample;
import com.yusys.agile.projectmanager.service.StaticProjectDataService;
import com.yusys.portal.model.common.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaticProjectDataServiceImpl implements StaticProjectDataService {


    @Autowired
    private SStaticProjectDataMapper sStaticProjectDataMapper;

    @Override
    public SStaticProjectData queryStaticProjectDataById(Long projectStatusId) {
        SStaticProjectDataExample sStaticProjectDataExample = new SStaticProjectDataExample();
        sStaticProjectDataExample.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andStaticProjectDataIdEqualTo(projectStatusId);
        return null;
    }
}

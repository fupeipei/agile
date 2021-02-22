package com.yusys.agile.utils;

import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoRole;
import com.yusys.portal.model.facade.enums.RoleCodeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleCheck {
    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    public Boolean checkPoRole(Long userId) {
        Boolean flag = false;
        //SsoUserDTO ssoUser = iFacadeUserApi.queryUserById(userId);
        SsoUserDTO ssoUser = new SsoUserDTO();
        if (Optional.ofNullable(ssoUser).isPresent()) {
            List<SsoRole> ssoRoles = ssoUser.getRoles();
            if (CollectionUtils.isNotEmpty(ssoRoles)) {
                for (SsoRole ssoRole : ssoRoles) {
                    if (RoleCodeEnum.PROJECT_ADMIN.getRoleCode().equals(ssoRole.getRoleCode())) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

}

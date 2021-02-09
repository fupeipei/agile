package com.yusys.agile.headerfielduser.service;

import com.yusys.agile.headerfielduser.domain.HeaderFieldUser;
import com.yusys.agile.headerfielduser.dto.HeaderFieldListDTO;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;
import java.util.Map;

public interface HeaderFieldUserService {
    /**
     * 功能描述
     *
     * @param securityDTO
     * @param category
     * @return java.util.List<com.yusys.agile.headerfielduser.domain.HeaderFieldUser>
     * @date 2020/4/17
     */
    List<HeaderFieldUser> queryVisibleHeaderFields(SecurityDTO securityDTO, Byte category, Byte isFilter);

    /**
     * 功能描述
     *
     * @param headerFieldListDTO
     * @param projectId
     * @return java.util.Map
     * @date 2020/4/17
     */
    Map updateHeaderFieldUserList(HeaderFieldListDTO headerFieldListDTO, Long projectId);


    Integer deleteCustomField(Long fieldId);
}

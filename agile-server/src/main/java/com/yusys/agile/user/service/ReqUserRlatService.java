package com.yusys.agile.user.service;

import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.user.domain.ReqUserRlat;

import java.util.List;

/**
 * 敏捷业务用户关系service
 *
 * @create 2020-08-12 16:35
 */
public interface ReqUserRlatService {

    /**
     * 功能描述: 批量新增用户关系
     *
     * @param reqUserRlats
     * @return void
     * @date 2020/8/12
     */
    void insertBatch(List<ReqUserRlat> reqUserRlats);

    /**
     * 功能描述: 删除
     *
     * @param subjectId
     * @return void
     * @date 2020/8/12
     */
    void deleteRlatsBySubjectId(Long subjectId);

    /**
     * 功能描述: 根据业务主键查询
     *
     * @param subjectId
     * @return java.util.List<com.yusys.agile.user.domain.ReqUserRlat>
     * @date 2020/8/13
     */
    List<ReqUserRlat> listRlatsBySubjectId(Long subjectId, Integer userRelateType, Integer isConcurrent);

    /**
     * 功能描述: 组装返回对象
     *
     * @param rlats
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2021/3/9
     */
    List<UserDTO> assembleUserDTOs(List<ReqUserRlat> rlats);

    /**
     * 功能描述: 根据用户id获取业务主键
     *
     * @param userId
     * @return java.util.List<Long>
     * @date 2020/12/1
     */
    List<Long> listReqUserRlatByUserId(Long userId, String tenantCode);

}

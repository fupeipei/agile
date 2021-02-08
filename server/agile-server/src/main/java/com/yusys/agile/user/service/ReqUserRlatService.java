package com.yusys.agile.user.service;

import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.user.domain.ReqUserRlat;

import java.util.List;

/**
 * 敏捷业务用户关系service
 *
 *
 * @create 2020-08-12 16:35
 */
public interface ReqUserRlatService {

    /**
     * 功能描述: 批量新增用户关系
     *
     * @date 2020/8/12
     * @param reqUserRlats
     * @return void
     */
    void insertBatch(List<ReqUserRlat> reqUserRlats);

    /**
     * 功能描述: 删除
     *
     * @date 2020/8/12
     * @param subjectId
     * @return void
     */
    void deleteRlatsBySubjectId(Long subjectId);

    /**
     * 功能描述: 根据业务主键查询
     *
     * @date 2020/8/13
     * @param subjectId
     * @return java.util.List<com.yusys.agile.user.domain.ReqUserRlat>
     */
    List<ReqUserRlat> listRlatsBySubjectId(Long subjectId,Integer userRelateType,Integer isConcurrent);

    /**
     * 功能描述: 组装返回对象
     *
     * @date 2021/3/9
     * @param rlats
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     */
    List<UserDTO> assembleUserDTOs(List<ReqUserRlat> rlats);

    /**
     * 功能描述: 根据用户id获取业务主键
     *
     * @date 2020/12/1
     * @param userId
     * @return java.util.List<Long>
     */
    List<Long> listReqUserRlatByUserId(Long userId,String tenantCode);
}

package com.yusys.agile.user.service.impl;

import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.user.dao.ReqUserRlatMapper;
import com.yusys.agile.user.domain.ReqUserRlat;
import com.yusys.agile.user.domain.ReqUserRlatExample;
import com.yusys.agile.user.enums.AgileUserRlatEnum;
import com.yusys.agile.user.service.ReqUserRlatService;
import com.google.common.collect.Lists;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 敏捷业务用户关系实现类
 *
 * @create 2020-08-12 16:35
 */
@Service("reqUserRlatService")
public class ReqUserRlatServiceImpl implements ReqUserRlatService {

    @Autowired
    private ReqUserRlatMapper reqUserRlatMapper;

    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    /**
     * 功能描述: 批量增加用户关系
     *
     * @param reqUserRlats
     * @return void
     * @date 2020/8/12
     */
    @Override
    public void insertBatch(List<ReqUserRlat> reqUserRlats) {
        reqUserRlatMapper.insertBatch(reqUserRlats);
    }

    /**
     * 功能描述: 根据业务主键删除对应的用户管联系信息
     * 目前全部删除，后期可能考虑负责人和组员的区别
     *
     * @param subjectId
     * @return void
     * @date 2020/8/12
     */
    @Override
    public void deleteRlatsBySubjectId(Long subjectId) {
        ReqUserRlatExample example = new ReqUserRlatExample();
        example.createCriteria().andSubjectIdEqualTo(subjectId);
        reqUserRlatMapper.deleteByExample(example);
    }

    /**
     * 功能描述:
     *
     * @param subjectId
     * @return java.util.List<com.yusys.agile.user.domain.ReqUserRlat>
     * @date 2020/8/13
     */
    @Override
    public List<ReqUserRlat> listRlatsBySubjectId(Long subjectId, Integer userRelateType, Integer isConcurrent) {
        ReqUserRlatExample example = new ReqUserRlatExample();
        ReqUserRlatExample.Criteria criteria = example.createCriteria().andSubjectIdEqualTo(subjectId).andUserRelateTypeEqualTo(userRelateType);
        // 是否负责人
        if (null != isConcurrent) {
            criteria.andIsConcurrentEqualTo(isConcurrent);
        }
        return reqUserRlatMapper.selectByExample(example);
    }

    /**
     * 功能描述: 组装返回对象
     *
     * @param rlats
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2021/3/9
     */
    @Override
    public List<UserDTO> assembleUserDTOs(List<ReqUserRlat> rlats) {
        List<Long> userIds = Lists.newArrayList();
        for (ReqUserRlat temp : rlats) {
            userIds.add(temp.getUserId());
        }
        // 只查询一次
        List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(userIds);
        List<UserDTO> dtos = Lists.newArrayList();
        for (SsoUser temp : ssoUsers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(temp.getUserId());
            userDTO.setUserName(temp.getUserName());
            dtos.add(userDTO);
        }

        return dtos;
    }

    /**
     * 功能描述: 根据用户id获取业务主键
     *
     * @param userId
     * @return java.util.List<Long>
     * @date 2020/12/1
     */
    @Override
    public List<Long> listReqUserRlatByUserId(Long userId, String tenantCode) {
        ReqUserRlatExample example = new ReqUserRlatExample();
        ReqUserRlatExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId).andStateEqualTo(StateEnum.U.getValue()).andUserRelateTypeEqualTo(AgileUserRlatEnum.REVIEW.CODE);
        List<ReqUserRlat> reqUserRlats = reqUserRlatMapper.selectByExample(example);

        List<Long> subjectIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(reqUserRlats)) {
            for (ReqUserRlat reqUserRlat : reqUserRlats) {
                subjectIds.add(reqUserRlat.getSubjectId());
            }
        }
        return subjectIds;
    }
}
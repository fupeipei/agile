package com.yusys.agile.businesskanban.service.impl;

import com.yusys.agile.businesskanban.dao.BusinessKanbanMapper;
import com.yusys.agile.businesskanban.dao.BusinessKanbanMembersMapper;
import com.yusys.agile.businesskanban.dao.BusinessMapper;
import com.yusys.agile.businesskanban.domain.*;
import com.yusys.agile.businesskanban.dto.BusinessKanbanDTO;
import com.yusys.agile.businesskanban.dto.BusinessKanbanMembersDTO;
import com.yusys.agile.businesskanban.enums.BusinessState;
import com.yusys.agile.businesskanban.service.BusinessKanbanService;
import com.google.common.collect.Lists;
import com.yusys.agile.utils.page.PageQuery;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Date: 2021/2/1
 * @Description:
 */
@Service
public class BusinessKanbanServiceImpl implements BusinessKanbanService {

    private static final Logger loggr = LoggerFactory.getLogger(BusinessKanbanServiceImpl.class);
    @Resource
    private BusinessKanbanMapper businessKanbanMapper;
    @Resource
    private BusinessKanbanMembersMapper businessKanbanMembersMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private BusinessMapper businessMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createBusinessKanban(BusinessKanbanDTO businessKanbanDTO) {
        //创建事务看板
        BusinessKanban businessKanban = new BusinessKanban();
        ReflectUtil.copyProperties(businessKanbanDTO, businessKanban);
        int i = businessKanbanMapper.insert(businessKanban);
        //如果人员不为空，插入看板成员
        List<Long> userIds = businessKanbanDTO.getUserIds();
        if (CollectionUtils.isNotEmpty(userIds)) {
            batchCreateKanbanMembers(businessKanban, userIds);
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBusinessKanban(Long kanbanId) {
        BusinessKanban businessKanban = businessKanbanMapper.selectByPrimaryKey(kanbanId);
        if(!Optional.ofNullable(businessKanban).isPresent()){
            throw new BusinessException("事务看板ID不存在,删除事务看板失败");
        }
        businessKanban.setState(StateEnum.E.getValue());
        int i = 0;
        try {
            i = businessKanbanMapper.updateByPrimaryKey(businessKanban);
        } catch (Exception e) {
            i = -1;
            loggr.info("BusinessKanbanServiceImpl deleteBusinessKanban error:{}", e.getMessage());
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBusinessKanban(BusinessKanbanDTO businessKanbanDTO) {
        BusinessKanban businessKanban = new BusinessKanban();
        ReflectUtil.copyProperties(businessKanbanDTO, businessKanban);
        List<Long> userIds = businessKanbanDTO.getUserIds();

        //删除事务看板成员
        BusinessKanbanMembersExample example = new BusinessKanbanMembersExample();
        example.createCriteria().andKanbanIdEqualTo(businessKanbanDTO.getKanbanId());
        /** 判断删除的人员是否有未完成的事务卡片*/
        List<BusinessKanbanMembers> businessKanbanMembers = businessKanbanMembersMapper.selectByExample(example);
        List<Long> oldUserIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(businessKanbanMembers)) {
            businessKanbanMembers.forEach(member -> oldUserIds.add(member.getUserId()));
        }
        List<Long> listC = oldUserIds.stream().filter(item -> !userIds.contains(item)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(listC)) {
            BusinessExample businessExample = new BusinessExample();
            businessExample.createCriteria().andStateEqualTo(StateEnum.U.toString())
                    .andBusinessStateNotEqualTo(BusinessState.COMPLETE.getNodeCode())
                    .andBusinessOwnerIn(listC);
            List<Business> businesses = businessMapper.selectByExample(businessExample);
            if (CollectionUtils.isNotEmpty(businesses)) {
                throw new BusinessException("删除的人员下有未处理的事务卡片!");
            }
        }

        businessKanbanMembersMapper.deleteByExample(example);
        if (CollectionUtils.isNotEmpty(userIds)) {
            batchCreateKanbanMembers(businessKanban, userIds);
        }
        return businessKanbanMapper.updateByPrimaryKey(businessKanban);
    }

    private void batchCreateKanbanMembers(BusinessKanban businessKanban, List<Long> userIds) {
        List<BusinessKanbanMembers> members = Lists.newArrayList();
        for (Long userId : userIds) {
            BusinessKanbanMembers member = new BusinessKanbanMembers();
            member.setKanbanId(businessKanban.getKanbanId());
            member.setUserId(userId);
            members.add(member);
        }
        businessKanbanMembersMapper.batchCreate(members);
    }


    @Override
    public List<BusinessKanbanDTO> getBusinessKanbanList(PageQuery<BusinessKanbanDTO> query) throws Exception {
        List<BusinessKanban> businessKanbans = businessKanbanMapper.selectByProjectId(query);
        List<BusinessKanbanDTO> businessKanbanDTOS = ReflectUtil.copyProperties4List(businessKanbans, BusinessKanbanDTO.class);
        dealDTO(businessKanbanDTOS);
        return businessKanbanDTOS;
    }

    @Override
    public List<BusinessKanbanDTO> selectBusinessKanbanListNoPage(BusinessKanbanDTO query) throws Exception {
        List<BusinessKanban> businessKanbans = businessKanbanMapper.selectByProjectIdNoPage(query);
        List<BusinessKanbanDTO> businessKanbanDTOS = ReflectUtil.copyProperties4List(businessKanbans, BusinessKanbanDTO.class);
        dealDTO(businessKanbanDTOS);
        return businessKanbanDTOS;
    }

    /**
     * @Date: 2021/2/1
     * @Description: 处理看板成员和用户名称
     * @Param: * @param businessKanbanDTOS
     * @Return: void
     */
    public void dealDTO(List<BusinessKanbanDTO> businessKanbanDTOS) throws Exception {
        if (CollectionUtils.isNotEmpty(businessKanbanDTOS)) {
            for (BusinessKanbanDTO kanbanDTO : businessKanbanDTOS) {
                //处理看板成员
                BusinessKanbanMembersExample example = new BusinessKanbanMembersExample();
                BusinessKanbanMembersExample.Criteria criteria = example.createCriteria();
                criteria.andKanbanIdEqualTo(kanbanDTO.getKanbanId());
                List<BusinessKanbanMembers> members = businessKanbanMembersMapper.selectByExample(example);
                List<Long> userIds = Lists.newArrayList();
                List<BusinessKanbanMembersDTO> membersDTOS = null;
                if (CollectionUtils.isNotEmpty(members)) {
                    members.forEach(member -> {
                        userIds.add(member.getUserId());
                    });

                    //处理用户名称
                    membersDTOS = ReflectUtil.copyProperties4List(members, BusinessKanbanMembersDTO.class);
                    List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(userIds);
                    for (BusinessKanbanMembersDTO membersDTO : membersDTOS) {
                        for (SsoUser ssoUser : ssoUsers) {
                            if (membersDTO.getUserId().equals(ssoUser.getUserId())) {
                                membersDTO.setUserName(ssoUser.getUserName());
                            }
                        }
                    }
                }
                kanbanDTO.setMembersDTOS(membersDTOS == null ? new ArrayList<>() : membersDTOS);
            }
        }
    }

    @Override
    public int countBusinessKanbanList(PageQuery<BusinessKanbanDTO> query) {
        return businessKanbanMapper.countBusinessKanbanList(query);
    }
}

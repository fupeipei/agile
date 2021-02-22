package com.yusys.agile.milestone.service.impl;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.milestone.dao.MilestoneMapper;
import com.yusys.agile.milestone.domain.Milestone;
import com.yusys.agile.milestone.domain.MilestoneExample;
import com.yusys.agile.milestone.dto.MilestoneDTO;
import com.yusys.agile.milestone.service.MilestoneService;
import com.yusys.agile.user.domain.ReqUserRlat;
import com.yusys.agile.user.enums.AgileUserRlatEnum;
import com.yusys.agile.user.service.ReqUserRlatService;
import com.google.common.collect.Lists;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 里程碑业务类
 *
 * @create 2020-08-12 15:49
 */
@Service("mileStoneService")
public class MileStoneServiceImpl implements MilestoneService {

    /**
     * log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MileStoneServiceImpl.class);

    @Resource
    private MilestoneMapper milestoneMapper;

    @Resource
    private ReqUserRlatService reqUserRlatService;

    @Resource
    private IFacadeUserApi iFacadeUserApi;


    /**
     * 功能描述: 创建里程碑
     *
     * @param milestoneDTO
     * @return void
     * @date 2020/8/12
     */
    @Override
    public MilestoneDTO addMilestone(MilestoneDTO milestoneDTO) {
        if (milestoneDTO == null || StringUtils.isBlank(milestoneDTO.getMilestoneName())
                || null == milestoneDTO.getProjectId()) {
            throw new BusinessException("入参错误！");
        }

        Date now = new Date();
        if (DateUtil.compare(now, milestoneDTO.getPlanFinishTime()) || DateUtil.compare(now, milestoneDTO.getRealFinishTime())) {
            throw new BusinessException("完成时间不能早于当前时间");
        }

        String milestoneName = milestoneDTO.getMilestoneName();
        Long projectId = milestoneDTO.getProjectId();
        // 查询同一项目下是否有名字相同的自定义字段
        if (checkSameName(milestoneName, projectId, null)) {
            throw new BusinessException("该里程碑名[" + milestoneName + "]在项目中已经存在！");
        }

        // 里程碑入库
        Milestone milestone = ReflectUtil.copyProperties(milestoneDTO, Milestone.class);
        // 默认新建未完成
        milestone.setMilestoneStatus(NumberConstant.ZERO);
        milestone.setState(StateEnum.U.getValue());
        milestoneMapper.insert(milestone);

        // 处理用户
        List<Long> userIds = milestoneDTO.getUserIds();
        if (CollectionUtils.isNotEmpty(userIds) && userIds.size() > 0) {
            List<ReqUserRlat> reqUserRlats = assembleReqUserRlats(userIds, milestone);
            reqUserRlatService.insertBatch(reqUserRlats);
        }

        // 返回对象
        return getMilestone(milestone.getMilestoneId());

    }

    /**
     * 功能描述: 组装关系对象
     *
     * @param userIds
     * @param milestone
     * @return java.util.List<com.yusys.agile.user.domain.ReqUserRlat>
     * @date 2020/8/13
     */
    private List<ReqUserRlat> assembleReqUserRlats(List<Long> userIds, Milestone milestone) {
        List<ReqUserRlat> reqUserRlats = Lists.newArrayList();
        for (Long userId : userIds) {
            ReqUserRlat reqUserRlat = new ReqUserRlat();
            reqUserRlat.setUserRelateType(AgileUserRlatEnum.MILESTONE.CODE);
            reqUserRlat.setSubjectId(milestone.getMilestoneId());
            reqUserRlat.setIsConcurrent(1);
            reqUserRlat.setUserId(userId);
            reqUserRlat.setProjectId(milestone.getProjectId());
            reqUserRlat.setState(StateEnum.U.getValue());
            reqUserRlats.add(reqUserRlat);
        }

        return reqUserRlats;
    }

    /**
     * 功能描述: 删除里程碑
     *
     * @param milestoneId
     * @return void
     * @date 2020/8/12
     */
    @Override
    public void deleteMilestone(Long milestoneId) {
        // 删除关联用户关系
        reqUserRlatService.deleteRlatsBySubjectId(milestoneId);
        // 删除里程碑信息
        Milestone record = new Milestone();
        record.setMilestoneId(milestoneId);
        record.setState(StateEnum.E.getValue());
        milestoneMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 功能描述: 修改里程碑
     *
     * @param milestoneDTO
     * @return void
     * @date 2020/8/13
     */
    @Override
    public MilestoneDTO editMilestone(MilestoneDTO milestoneDTO) {
        if (milestoneDTO == null || StringUtils.isBlank(milestoneDTO.getMilestoneName())
                || null == milestoneDTO.getProjectId()) {
            throw new BusinessException("入参错误！");
        }

        Date now = new Date();
        // 修改里程碑时实际完成时间不能在当前日期之前
        if (NumberConstant.ONE.equals(milestoneDTO.getMilestoneStatus()) && null != milestoneDTO.getRealFinishTime()
                && DateUtil.compare(now, milestoneDTO.getRealFinishTime())) {
            throw new BusinessException("完成时间不能早于当前时间");
        }


        String milestoneName = milestoneDTO.getMilestoneName();
        Long projectId = milestoneDTO.getProjectId();
        // 查询同一项目下是否有名字相同的自定义字段
        if (checkSameName(milestoneName, projectId, milestoneDTO.getProjectId())) {
            throw new BusinessException("该里程碑名[" + milestoneName + "]在项目中已经存在！");
        }

        // 处理用户
        List<Long> userIds = milestoneDTO.getUserIds();
        // 删除关联用户关系
        reqUserRlatService.deleteRlatsBySubjectId(milestoneDTO.getMilestoneId());
        Milestone milestone = ReflectUtil.copyProperties(milestoneDTO, Milestone.class);
        if (CollectionUtils.isNotEmpty(userIds) && userIds.size() > 0) {
            List<ReqUserRlat> reqUserRlats = assembleReqUserRlats(userIds, milestone);
            reqUserRlatService.insertBatch(reqUserRlats);
        }

        Milestone record = ReflectUtil.copyProperties(milestoneDTO, Milestone.class);
        // 里程碑状态为完成时，实际完成时间不选的话选择系统时间
        if (NumberConstant.ONE.equals(record.getMilestoneStatus()) && null == record.getRealFinishTime()) {
            record.setRealFinishTime(now);
        }

        // 里程碑修改成未完成时，必须要将实际完成时间清掉
        if (NumberConstant.ZERO.equals(record.getMilestoneStatus())) {
            record.setRealFinishTime(null);
        }

        milestoneMapper.updateByPrimaryKeySelectiveWithNull(record);

        // 返回对象
        return getMilestone(record.getMilestoneId());

    }

    /**
     * 功能描述: 查询里程碑详情
     *
     * @param milestoneId
     * @return com.yusys.agile.milestone.dto.MilestoneDTO
     * @date 2020/8/13
     */
    @Override
    public MilestoneDTO getMilestone(Long milestoneId) {
        Milestone milestone = milestoneMapper.selectByPrimaryKey(milestoneId);
        MilestoneDTO milestoneDTO = ReflectUtil.copyProperties(milestone, MilestoneDTO.class);

        // 查询用户信息
        List<ReqUserRlat> rlats = reqUserRlatService.listRlatsBySubjectId(milestoneDTO.getMilestoneId(), AgileUserRlatEnum.MILESTONE.CODE, 1);

        List<UserDTO> userDTOS = assembleUserDTOs(rlats);
        milestoneDTO.setUsers(userDTOS);
        return milestoneDTO;
    }

    /**
     * 功能描述:列表展示里程碑信息
     *
     * @param projectId
     * @return java.util.List<com.yusys.agile.milestone.dto.MilestoneDTO>
     * @date 2020/8/17
     */
    @Override
    public List<MilestoneDTO> listMilestones(Long projectId) {
        MilestoneExample example = new MilestoneExample();
        MilestoneExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId)
                .andStateEqualTo(StateEnum.U.getValue());

        example.setOrderByClause("plan_finish_time asc,create_time asc");

        List<Milestone> records = milestoneMapper.selectByExample(example);
        List<MilestoneDTO> milestoneDTOS = Lists.newArrayList();
        Date now = new Date();
        for (Milestone milestone : records) {
            MilestoneDTO milestoneDTO = ReflectUtil.copyProperties(milestone, MilestoneDTO.class);
            // 查询用户信息
            List<ReqUserRlat> rlats = reqUserRlatService.listRlatsBySubjectId(milestoneDTO.getMilestoneId(), AgileUserRlatEnum.MILESTONE.CODE, 1);
            List<UserDTO> userDTOS = assembleUserDTOs(rlats);
            milestoneDTO.setUsers(userDTOS);
            // 是否超时 0未超时 1超时
            milestoneDTO.setIsTimeout(DateUtil.compare(now, milestoneDTO.getPlanFinishTime()) ? NumberConstant.ONE : NumberConstant.ZERO);
            milestoneDTOS.add(milestoneDTO);
        }

        return milestoneDTOS;
    }

    /**
     * 功能描述: 转换成userDto
     *
     * @param rlats
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2020/8/13
     */
    private List<UserDTO> assembleUserDTOs(List<ReqUserRlat> rlats) {
        List<Long> userIds = Lists.newArrayList();
        for (ReqUserRlat temp : rlats) {
            userIds.add(temp.getUserId());
        }

        List<UserDTO> dtos = Lists.newArrayList();
        List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(userIds);

        for (SsoUser temp : ssoUsers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(temp.getUserId());
            userDTO.setUserName(temp.getUserName());
            dtos.add(userDTO);
        }

        return dtos;
    }


    /**
     * 功能描述: 判断是否名称重复
     *
     * @param milestoneName
     * @param projectId
     * @param id
     * @return boolean
     * @date 2020/8/12
     */
    private boolean checkSameName(String milestoneName, Long projectId, Long id) {
        MilestoneExample example = new MilestoneExample();
        MilestoneExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId)
                .andMilestoneNameEqualTo(milestoneName).andStateEqualTo(StateEnum.U.getValue());

        if (null != id) {
            criteria.andMilestoneIdEqualTo(id);
        }

        List<Milestone> list = milestoneMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return true;
        }

        return false;
    }
}
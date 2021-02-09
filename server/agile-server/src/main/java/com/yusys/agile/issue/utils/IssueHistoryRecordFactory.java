package com.yusys.agile.issue.utils;

import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.module.dao.ModuleMapper;
import com.yusys.agile.module.domain.Module;
import com.yusys.agile.module.domain.ModuleExample;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 工作项历史记录封装类以及相关方法
 */
@Component
public class IssueHistoryRecordFactory {
    @Autowired
    private SprintMapper sprintMapper;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private KanbanStageInstanceMapper stageInstanceMapper;

    /**
     * 操作历史公共封装方法
     *
     * @param issueId        工作项ID
     * @param isCustem       IsCustomEnum.java枚举
     * @param recordType     IssueHistoryRecordTypeEnum.java 操作类型
     * @param operationField 操作属性
     * @return
     */
    public static IssueHistoryRecord createHistoryRecord(Long issueId, Byte isCustem,
                                                         Byte recordType, String operationField) {
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        IssueHistoryRecord issueHistoryRecord = new IssueHistoryRecord();
        issueHistoryRecord.setIssueId(issueId);
        issueHistoryRecord.setRecordType(recordType);
        issueHistoryRecord.setIsCustom(isCustem);
        issueHistoryRecord.setOperationField(operationField);
        issueHistoryRecord.setCreateUid(userInfo.getUserId());
        issueHistoryRecord.setTenantCode(tenantCode);
        return issueHistoryRecord;
    }

    /**
     * 根据迭代ID查询迭代信息，不为空，将迭代封装成Map
     *
     * @param sprintId
     * @param sprintMap
     */
    public void getSprintMapInfo(Long sprintId, Map<String, String> sprintMap) {
        SprintWithBLOBs sprint = sprintMapper.selectByPrimaryKeyNotText(sprintId);
        if (Optional.ofNullable(sprint).isPresent()) {
            sprintMap.put(sprintId.toString(), sprint.getSprintName());
        }
    }

    /**
     * 将产品ID转换为文字描述
     *
     * @param value
     * @return
     */
    public String getSystemInfo(String value) {
        List<String> projectNames = new ArrayList<>();
        String oldValue = value.replace("[", "").replace("]", "");
        if (StringUtils.isNotBlank(oldValue)) {
            String[] moduleIds = oldValue.split(",");
            for (String systemId : moduleIds) {
                SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(Long.valueOf(systemId));
                if (Optional.ofNullable(ssoSystem).isPresent()) {
                    projectNames.add(ssoSystem.getSystemName());
                }
            }
        }
        return projectNames.toString().replace("[", "").replace("]", "");
    }

    /**
     * 将多个模块ID，转换为模块名称
     *
     * @param value
     * @return
     */
    public String getModuleInfos(String value) {
        List<String> moduleName = new ArrayList<>();
        String oldValue = value.replace("[", "").replace("]", "");
        List<Long> moduleIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(oldValue)) {
            String[] moduleIds = oldValue.split(",");
            for (String moduleId : moduleIds) {
                moduleIdList.add(Long.valueOf(moduleId));
            }
        }
        if (CollectionUtils.isNotEmpty(moduleIdList)) {
            ModuleExample example = new ModuleExample();
            example.createCriteria().andModuleIdIn(moduleIdList);
            List<Module> modules = moduleMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(modules)) {
                modules.forEach(module -> {
                    moduleName.add(module.getModuleName());
                });
            }
        }
        return moduleName.toString().replace("[", "").replace("]", "");
    }

    /**
     * 根据项目ID查询阶段状态，并封装成Map<String,String>
     * key为阶段ID，value为阶段名称，
     *
     * @param projectId
     * @return
     */
    public Map<String, String> getStagesInstanceMapInfo(Long projectId) {
        Map<String, String> stageInstanceMap = new HashMap<>();
        KanbanStageInstanceExample instanceExample = new KanbanStageInstanceExample();
        instanceExample.createCriteria().andStateEqualTo(StateEnum.U.toString()).andProjectIdEqualTo(projectId);
        List<KanbanStageInstance> stageInstances = stageInstanceMapper.selectByExample(instanceExample);
        if (CollectionUtils.isNotEmpty(stageInstances)) {
            for (KanbanStageInstance stageInstance : stageInstances) {
                Long parentId = stageInstance.getParentId();
                Long stageId = stageInstance.getStageId();
                String stageName = stageInstance.getStageName();
                if (parentId == -1) {
                    stageInstanceMap.put(stageId.toString(), stageName);
                } else {
                    stageInstanceMap.put(parentId + "-" + stageId, stageInstanceMap.get(parentId + "") + ">" + stageName);
                }
            }
        }
        return stageInstanceMap;
    }

}

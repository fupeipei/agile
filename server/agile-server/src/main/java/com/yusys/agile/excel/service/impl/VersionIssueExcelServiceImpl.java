package com.yusys.agile.excel.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yusys.agile.excel.domain.VersionIssue;
import com.yusys.agile.excel.enums.DeployDescEnum;
import com.yusys.agile.excel.service.VersionIssueExcelService;
import com.yusys.agile.versionmanager.dao.VersionIssueSysExtendMapper;
import com.yusys.agile.versionmanager.dto.VersionIssueDTO;
import com.yusys.agile.versionmanager.dto.VersionIssueSysExtendFieldDetailDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * @Date: 2021/2/2
 */
@Service("VersionIssueExcelService")
public class VersionIssueExcelServiceImpl implements VersionIssueExcelService {

    @Resource
    private VersionIssueSysExtendMapper versionIssueSysExtendMapper;

    @Override
    public void exportIssueDatas(VersionIssue versionIssue, HttpServletResponse response) throws Exception {
        if (null == versionIssue.getVersionIdList()) {
            return;
        }
        List<VersionIssueDTO> versionIssueDTOS = versionIssueSysExtendMapper.selectEpicIssueByVersionId(versionIssue.getVersionIdList());
        List<Long> issueIds = new ArrayList<>();
        Map<Long, VersionIssueDTO> issueDTOMap = new LinkedHashMap();
        if (isNotEmpty(versionIssueDTOS)) {
            versionIssueDTOS.forEach(issueDTO -> {
                issueDTOMap.put(issueDTO.getIssueId(), issueDTO);
                issueIds.add(issueDTO.getIssueId());
            });
        }
        if (isNotEmpty(issueIds)) {
            List<VersionIssueSysExtendFieldDetailDTO> sysExtendFieldDetailDTOS = versionIssueSysExtendMapper.selectFeatureIssueByEpicId(issueIds);
            if (isNotEmpty(sysExtendFieldDetailDTOS)) {
                sysExtendFieldDetailDTOS.forEach(sysExtendFieldDetailDTO -> {
                    Long parentId = sysExtendFieldDetailDTO.getParentId();
                    String systemName = sysExtendFieldDetailDTO.getSystemName();
                    String bizStatus = sysExtendFieldDetailDTO.getBizStatus();
                    String handlerName = sysExtendFieldDetailDTO.getHandlerName();
                    String baUserName = sysExtendFieldDetailDTO.getBaUserName();
                    String actualFinishTime = sysExtendFieldDetailDTO.getActualFinishTime();
                    String debugActualFinishTime = sysExtendFieldDetailDTO.getDebugActualFinishTime();
                    String deployIllustration = sysExtendFieldDetailDTO.getDeployIllustration();
                    if (StringUtils.isNotEmpty(deployIllustration)) {
                        deployIllustration = deployIllustration.replace("[", "").replace("]", "").replace("\"", "");
                    }
                    if (issueDTOMap.containsKey(parentId)) {
                        VersionIssueDTO issueDTO = issueDTOMap.get(parentId);
                        if (StringUtils.isNotEmpty(systemName)) {
                            switch (systemName) {
                                case "BOSS":
                                    issueDTO.setBossBizStatus(bizStatus);
                                    issueDTO.setBossBa(handlerName);
                                    issueDTO.setBossDutyUser(baUserName);
                                    issueDTO.setBossActualFinishTime(actualFinishTime);
                                    issueDTO.setBossDebugActualFinishTime(debugActualFinishTime);
                                    List<String> deploys = new ArrayList<>();
                                    if (StringUtils.isNotEmpty(deployIllustration)) {
                                        String[] deployDescList = deployIllustration.split(",");
                                        Arrays.stream(deployDescList).forEach(code -> deploys.add(DeployDescEnum.getDesc(code)));
                                        issueDTO.setBossDeployDesc(String.join("", deploys));
                                    }
                                    break;
                                case "NGCRM":
                                    issueDTO.setCrmBizStatus(bizStatus);
                                    issueDTO.setCrmBa(handlerName);
                                    issueDTO.setCrmDutyUser(baUserName);
                                    issueDTO.setBossDeployDesc(deployIllustration);
                                    issueDTO.setCrmActualFinishTime(actualFinishTime);
                                    issueDTO.setCrmDebugActualFinishTime(debugActualFinishTime);
                                    break;
                                case "电子商务":
                                    issueDTO.setBusinessBizStatus(bizStatus);
                                    issueDTO.setBusinessBa(handlerName);
                                    issueDTO.setBusinessDutyUser(baUserName);
                                    issueDTO.setBossDeployDesc(deployIllustration);
                                    issueDTO.setBusinessActualFinishTime(actualFinishTime);
                                    issueDTO.setBusinessDebugActualFinishTime(debugActualFinishTime);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
            }
        }
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.renameSheet(0, "版本管理");
        writerComDemandHeaderTitle(writer);
        // 一次性写出内容，使用默认样式
        //writer.setHeaderAlias(headerAliasP);
        writer.write(versionIssueDTOS);
        //设置所有列为自动宽度，不考虑合并单元格
        //writer.autoSizeColumnAll();
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + "version.xlsx";
        responseExport(response, fileName, writer);
    }


    public void writerComDemandHeaderTitle(ExcelWriter writer) {
        writer.addHeaderAlias("versionName", "版本名称");
        writer.addHeaderAlias("approvalStatus", "局方审批状态");
        writer.addHeaderAlias("approvalResult", "局方审批结果");
        writer.addHeaderAlias("versionIsRemove", "是否移除");
        writer.addHeaderAlias("issueId", "业务需求id");
        writer.addHeaderAlias("bizNum", "客户需求编号");
        writer.addHeaderAlias("reqGroup", "局方分组");
        writer.addHeaderAlias("responsiblePerson", "局方需求负责人");
        writer.addHeaderAlias("formalReqCode", "局方需求编号");
        writer.addHeaderAlias("bizName", "业务需求名称");
        writer.addHeaderAlias("bizPlanStates", "需求计划状态");
        writer.addHeaderAlias("bizStatus", "需求状态");
        writer.addHeaderAlias("crmBizStatus", "CRM需求状态");
        writer.addHeaderAlias("crmBa", " CRM-BA");
        writer.addHeaderAlias("crmDutyUser", "CRM测试负责人");
        writer.addHeaderAlias("crmActualFinishTime", "CRM-BA实际完成时间");
        writer.addHeaderAlias("crmDebugActualFinishTime", "CRM联调测试实际完成时间");
        writer.addHeaderAlias("bossBizStatus", "BOSS需求状态");
        writer.addHeaderAlias("bossBa", "BOSS-BA");
        writer.addHeaderAlias("bossDutyUser", "BOSS测试负责人");
        writer.addHeaderAlias("bossDeployDesc", "BOSS部署说明");
        writer.addHeaderAlias("bossActualFinishTime", "BOSS-BA实际完成时间");
        writer.addHeaderAlias("bossDebugActualFinishTime", "BOSS联调测试实际完成时间");
        writer.addHeaderAlias("businessBizStatus", "电商需求状态");
        writer.addHeaderAlias("businessBa", "电商-BA");
        writer.addHeaderAlias("businessDutyUser", "电商测试负责人");
        writer.addHeaderAlias("businessActualFinishTime", "电商-BA实际完成时间");
        writer.addHeaderAlias("businessDebugActualFinishTime", "电商联调测试实际完成时间");

    }

    private void responseExport(HttpServletResponse response, String fileName, ExcelWriter writer) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out);
        // 关闭writer，释放内存
        writer.close();
    }
}

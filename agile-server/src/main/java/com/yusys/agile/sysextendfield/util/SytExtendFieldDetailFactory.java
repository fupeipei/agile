package com.yusys.agile.sysextendfield.util;

import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.service.SysExtendFieldDetailService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @description
 * @date 2021/3/18
 */
@Component
public class SytExtendFieldDetailFactory {
    @Resource
    private SysExtendFieldDetailService sysExtendFieldDetailService;

    public int insertOrUpdateIssueExtendFieldDetail(Long bizBacklogId,String filedId,String fieldName,String value) {
        int result;
        SysExtendFieldDetail sysExtendFieldDetail = sysExtendFieldDetailService.getSysExtendFieldDetail(bizBacklogId, filedId);
        if(null == sysExtendFieldDetail){
            sysExtendFieldDetail = new SysExtendFieldDetail();
            sysExtendFieldDetail.setFieldId(filedId);
            if(null != fieldName){
                sysExtendFieldDetail.setFieldName(fieldName);
            }
            sysExtendFieldDetail.setIssueId(bizBacklogId);
            sysExtendFieldDetail.setValue(value);
            result = sysExtendFieldDetailService.save(sysExtendFieldDetail);
        }else{
            sysExtendFieldDetail.setValue(value);
            result = sysExtendFieldDetailService.update(sysExtendFieldDetail);
        }
        return result;
    }
}

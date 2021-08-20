package com.yusys.agile.externalapiconfig.dao.util;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.externalapiconfig.dao.ExternalApiConfigMapper;
import com.yusys.agile.fault.domain.ExternalApiConfig;
import com.yusys.agile.fault.domain.ExternalApiConfigExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @date 2021/2/2
 */
@Component
public class ExternalApiConfigUtil {
    @Resource
    private ExternalApiConfigMapper externalApiConfigMapper;

    public String getPropValue(String key) {
        ExternalApiConfigExample externalApiConfigExample = new ExternalApiConfigExample();
        externalApiConfigExample.createCriteria().andFieldNameEqualTo(key);
        List<ExternalApiConfig> configs = externalApiConfigMapper.selectByExample(externalApiConfigExample);
        if (CollectionUtils.isEmpty(configs)) {
            return null;
        }
        return configs.get(NumberConstant.ZERO).getFieldValue().trim();
    }

}

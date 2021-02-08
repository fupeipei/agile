package com.yusys.agile.utils;

import com.yusys.agile.fault.domain.YuItBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * YuIt同步工具类
 *
 *     
 * @create 2020-04-24 10:00
 */
@Component
public class YuItUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(YuItUtil.class);

    public static final String YuIt = "YuIt";

    @Value("${fault.source}")
    private String faultSource;

    private static YuItBean itBean = new YuItBean();

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        itBean.setSource(faultSource);
    }

    /**
     * 功能描述: 判断是否是YuIt同步缺陷数据
     *
     * @param
     * @return java.lang.Boolean
     *     
     * @date 2020/4/24
     */
    public static Boolean yuItSync() {
        if (StringUtils.equalsIgnoreCase(itBean.getSource(), YuIt)) {
            LOGGER.debug("缺陷模式：YuIt同步缺陷");
            return true;
        }

        LOGGER.debug("缺陷模式：YuDO自建缺陷");
        return false;
    }

}
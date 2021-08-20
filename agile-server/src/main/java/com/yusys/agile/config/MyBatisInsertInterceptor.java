package com.yusys.agile.config;

import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.utils.YuItUtil;
import com.yusys.portal.common.id.IdGenerator;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: MyBatisInsertInterceptor
 * @Description: 使用mybatis执行数据库insert操作时候，自动设置主键，需要在mapper.xml中设置useGeneratorKey=true
 * @CreateDate: 2021/2/1
 * @Version 1.0
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MyBatisInsertInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisInsertInterceptor.class);

    private IdGenerator idGenerator;

    private static final Long DEFAULT_WORK_ID = 1L;

    private static final Long DEFAULT_DATA_CENTER_ID = 1L;

    private static final String CREATE_TIME_STR = "createTime";

    private static final String CREATE_UID_STR = "createUid";

    private static final String PARAM1_STR = "param1";


    @Value("${uuidFlag}")
    private String uuidFlag;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        //如果是单一主键，则在插入前自动获取主键对应的属性，通过ID生成器生成对应的主键
        String keyProp = "";
        if (null != mappedStatement.getKeyProperties() && mappedStatement.getKeyProperties().length == 1) {
            keyProp = mappedStatement.getKeyProperties()[0];
        }

        if (parameter instanceof MapperMethod.ParamMap) {
            //支持mybatis批量插入时候设置主键
            if (((MapperMethod.ParamMap) parameter).get(PARAM1_STR) instanceof List) {
                List<Object> tempList = (List<Object>) ((MapperMethod.ParamMap) parameter).get(PARAM1_STR);
                for (Object each : tempList) {
                    setFieldValue(mappedStatement, keyProp, each);
                }
            }
        } else {
            //设置主键，及默认字段值
            setFieldValue(mappedStatement, keyProp, parameter);
        }

        return invocation.proceed();
    }

    private void setFieldValue(MappedStatement mappedStatement, String keyProperty, Object parameter) throws Throwable {
        if (StringUtils.isNotBlank(uuidFlag)) {
            ReflectUtil.setFieldValue(parameter, keyProperty, idGenerator.nextId());
        }
        //设置新增数据时需要插入的值
        if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
            ReflectUtil.setFieldValue(parameter, "createTime", new Date());
            ReflectUtil.setFieldValue(parameter, "createDate", new Date());
            ReflectUtil.setFieldValue(parameter, "modifyDate", new Date());
            //ReflectUtil.setFieldValue(parameter, "tenantCode", UserThreadLocalUtil.getTenantCode());
            // 采用yuIt同步时，创建人和创建时间要采用yuIt传过来的
            if (!isyuItFault(parameter, CREATE_TIME_STR)) {
                ReflectUtil.setFieldValue(parameter, CREATE_TIME_STR, new Date());
            }
            if (!isyuItFault(parameter, CREATE_UID_STR)) {
                if (!checkCreateUidIsNotNull(parameter, CREATE_UID_STR)) {
                    ReflectUtil.setFieldValue(parameter, CREATE_UID_STR, UserThreadLocalUtil.getUserInfo().getUserId());
                }
            }
            if (null == ReflectUtil.getFieldValue(parameter, "tenantCode")) {
                ReflectUtil.setFieldValue(parameter, "tenantCode", UserThreadLocalUtil.getTenantCode());
            }
            ReflectUtil.setFieldValue(parameter, "operatorId", String.valueOf(UserThreadLocalUtil.getUserInfo().getUserId()));
            ReflectUtil.setFieldValue(parameter, "operatorName", UserThreadLocalUtil.getUserInfo().getUserName());
            ReflectUtil.setFieldValue(parameter, "state", StateEnum.U.getValue());
        }
        //设置修改数据时需要插入的值
        if (SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
            ReflectUtil.setFieldValue(parameter, "updateTime", new Date());
            ReflectUtil.setFieldValue(parameter, "updateUid", UserThreadLocalUtil.getUserInfo().getUserId());
            ReflectUtil.setFieldValue(parameter, "modifyDate", new Date());
            ReflectUtil.setFieldValue(parameter, "operatorId", String.valueOf(UserThreadLocalUtil.getUserInfo().getUserId()));
            ReflectUtil.setFieldValue(parameter, "operatorName", UserThreadLocalUtil.getUserInfo().getUserName());
            if (null == ReflectUtil.getFieldValue(parameter, "tenantCode")) {
                ReflectUtil.setFieldValue(parameter, "tenantCode", UserThreadLocalUtil.getTenantCode());
            }
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        if (null == idGenerator) {
            String workerId = properties.getProperty("workerId");
            String dataCenterId = properties.getProperty("dataCenterId");
            if (StringUtils.isNumeric(workerId) && StringUtils.isNumeric(dataCenterId)) {
                LOGGER.info("create id generator instance use workerId:{} dataCenterId:{}", workerId, dataCenterId);
                idGenerator = new IdGenerator(Long.parseLong(workerId), Long.parseLong(dataCenterId));
            } else {
                LOGGER.error("illegal workerId or dataCenterId, number required. workerId:{} and dataCenterId:{} provide.",workerId,dataCenterId);
                LOGGER.info("create id generator instance use default workerId:{} and dataCenterId:{}", DEFAULT_WORK_ID,
                        DEFAULT_DATA_CENTER_ID);
                idGenerator = new IdGenerator(DEFAULT_WORK_ID, DEFAULT_DATA_CENTER_ID);
            }
        }
    }


    /**
     * 功能描述: 判断是否yuIt同步缺陷且参数传来创建人和创建时间是否为空
     * 用来过滤是否采用系统用户还是采用yuIt传来的创建用户及时间
     *
     * @param parameter
     * @param fieldName
     * @return java.lang.Boolean
     * @date 2021/2/1
     */
    private static Boolean isyuItFault(Object parameter, String fieldName) {
        //   yuIt同步且新增缺陷时
        if (YuItUtil.yuItSync() && (parameter instanceof Issue)) {
            try {
                Byte issueType = (Byte) ReflectUtil.getFieldValue(parameter, "issueType");
                if ((IssueTypeEnum.TYPE_FAULT.CODE.equals(issueType))
                        && null != ReflectUtil.getFieldValue(parameter, fieldName)) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.error("e= {}", e.getMessage());
            }
        }

        return false;


    }

    /**
     * @param fieldName
     * @Date: 2021/2/1
     * @Description: 校验创建人ID是否为空
     * @Param: * @param parameter
     * @Return: java.lang.Boolean
     */
    private Boolean checkCreateUidIsNotNull(Object parameter, String fieldName) {
        if (parameter instanceof Issue) {
            try {
                Long createUid = (Long) ReflectUtil.getFieldValue(parameter, fieldName);
                if (null != createUid) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.error("e={}", e.getMessage());
            }
        }
        return false;
    }

}

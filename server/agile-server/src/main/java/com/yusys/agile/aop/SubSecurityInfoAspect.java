package com.yusys.agile.aop;

import cn.hutool.core.util.ReflectUtil;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


/**
 * @ClassName: SubSecurityInfoAspect
 * @Description: AOP controller切面, 如果发现有SecurityDTO对象, 则自动注入对应属性
 * @CreateDate: 2021/2/1
 * @Version 1.0
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class SubSecurityInfoAspect {
    private final static Logger log = LoggerFactory.getLogger(SubSecurityInfoAspect.class);


    @Pointcut("execution(* com.yusys.*.*.rest.*.*(..))")
    private void securityAspect() {

    }


    @Around("securityAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();
        if (null != methodArgs && methodArgs.length > 0) {
            for (Object each : methodArgs) {
                if (each instanceof SecurityDTO) {
                    //从threadLocal中获取用户信息
                    SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
                    try {
                        if (userInfo == null) {
                            return joinPoint.proceed();
                        }
                        ReflectUtil.setFieldValue(each, "userName", userInfo.getUserName());
                        ReflectUtil.setFieldValue(each, "userAcct", userInfo.getUserAcct());
                        ReflectUtil.setFieldValue(each, "userId", userInfo.getUserId());
                        ReflectUtil.setFieldValue(each, "tenantCode", userInfo.getTenantCode());
                        ReflectUtil.setFieldValue(each, "projectId", userInfo.getProjectId());
                        ReflectUtil.setFieldValue(each, "roleIds", userInfo.getRoleIds());

                    } catch (Exception e) {
                        log.error("设置用户名失败", e);
                    }
                }
            }
            return joinPoint.proceed(methodArgs);
        } else {
            return joinPoint.proceed();
        }
    }
}

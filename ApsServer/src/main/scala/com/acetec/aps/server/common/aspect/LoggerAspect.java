package com.acetec.aps.server.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {

    @Around("execution( * com.acetec.aps.server.service.impl.AuthenticateServiceImpl.*(..)) && @annotation(com.acetec.aps.server.common.aspect.Loggable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        Logger log = LoggerFactory.getLogger(point.getThis().getClass());
        log.info(
                "#{}({}): {} in %[msec]s",
                MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
                point.getArgs(),
                result,
                System.currentTimeMillis() - start
        );
        return result;
    }
}


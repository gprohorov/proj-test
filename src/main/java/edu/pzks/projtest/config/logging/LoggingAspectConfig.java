package edu.pzks.projtest.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

@Aspect
@Component
@Slf4j
public class LoggingAspectConfig {
//    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoggingAspectConfig.class);
    private final Map<Throwable, Boolean> loggedExceptions = Collections.synchronizedMap(new WeakHashMap<>());
    @Pointcut("execution(* sm.pro.smrestapi..*.*(..)) && !within(sm.pro.smrestapi.security.JwtFilter)")
    public void methodsPointcut() {}

    @Before("methodsPointcut()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Entering method: {}.{} with arguments: {}",
                className, methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "methodsPointcut()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Method {}.{} completed successfully with result: {}",
                className, methodName, result);
    }

    @AfterThrowing(pointcut = "methodsPointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        if (ex == null || loggedExceptions.containsKey(ex)) {
            return;
        }
        if (loggedExceptions.size() > 25) {
            loggedExceptions.clear();
        }
        loggedExceptions.put(ex, true);
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error("Exception occurred in {}.{}: {} - {}",
                className, methodName, ex.getClass().getSimpleName(), ex.getMessage(), ex);

        if (ex.getCause() != null) {
            log.error("Caused by: {}", ex.getCause().getMessage());
        }
    }
}

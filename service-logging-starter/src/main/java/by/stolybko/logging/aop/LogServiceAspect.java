package by.stolybko.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Slf4j
@Aspect
public class LogServiceAspect {


    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void isServiceLayer() {
    }

    @Around(value = "isServiceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {


        log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();

            log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);

            return result;

        } catch (Throwable ex) {
            log.info("Exception: {}: {}, when invoke {}.{}() with argument[s] = {}",
                    ex.getClass(), ex.getMessage(), joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
            throw ex;
        }
    }

}

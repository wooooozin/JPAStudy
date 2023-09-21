package com.example.jpa.common.aop;

import com.example.jpa.logs.service.LogsService;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.model.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class LoginLogger {
    private final LogsService logsService;

    @Around("execution(* com.example.jpa..*.*Service*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("################");
        log.info("################");
        log.info("서비스 호출");

        Object result = joinPoint.proceed();

        if ("login".equals(joinPoint.getSignature().getName())) {
            Object[] args = joinPoint.getArgs();
            StringBuilder sb = new StringBuilder();
            if (args != null || args.length > 0) {
                for (Object o : args) {
                    if (o instanceof UserLogin) {
                        sb.append(((UserLogin) o).getEmail());
                        sb.append("\n");
                        sb.append("리턴값 : " + ((AppUser)result).getUserName());
                    }
                }
            }
            logsService.add(sb.toString());
        }


        log.info("################");
        log.info("################");
        log.info("서비스 호출 후후후후");

        return result;
    }

}

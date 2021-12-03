package com.gjy.boke.aspect;


import com.sun.org.apache.xpath.internal.objects.XObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 处理日志的切面类
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 切面，以下配置的是截获controller目录下的所有方法
     */
    @Pointcut("execution(* com.gjy.boke.controller.*.*(..))")
    public void log() {}

    /**
     *在切面方法执行前执行该前置方法
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //获取HttpServlerRequest
        ServletRequestAttributes sr= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sr.getRequest();
        String url = String.valueOf(request.getRequestURL());
        System.out.println(url);
        String ip = request.getRemoteAddr();
        //通过切面对象获取拦截到的是哪一个方法
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获取请求参数
        Object[] args = joinPoint.getArgs();
        ResultLog requestLog = new ResultLog(url, ip, classMethod, args);
        System.out.println("Request {} "+requestLog);

        //统计系统UV,游客需根据ip地址进行统计，普通用户根据用户id进行统计
        HyperLogLogOperations log = redisTemplate.opsForHyperLogLog();
        log.add("tourist",ip);

    }

    /**
     * 在我们拦截到方法执行到返回前拦截，即捕获我们拦截到的方法的返回内容
     * 如此，通过该方法我们就可以获取到controller中的方法的返回内容
     * @param result
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturning(Object result){
        //result为请求的页面名称
        logger.info("Result {}",result);
    }

    private class ResultLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ResultLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}

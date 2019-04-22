package com.galaxis.wcs.yanfeng.aop;

import com.galaxis.wcs.yanfeng.database.oes.service.MessageService;
import com.galaxis.wcs.yanfeng.exception.OesException;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PlcSeqAspect {
    private static final Logger log = LoggerFactory.getLogger(PlcSeqAspect.class);

    @Autowired
    private MessageService messageService;

    @Pointcut("@annotation(com.galaxis.wcs.yanfeng.aop.PlcSeqAnnotation)")
    public void plcSeqPointCut() {
    }

    @Around(value = "plcSeqPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获得request
        Object[] args = joinPoint.getArgs();
        LineRequest request = (LineRequest) args[0];
        LineResponse response = (LineResponse) args[1];
        // 缓存老的seq, 并赋值新的seq
        Integer oldSeq = request.getSeq();
        Integer seq = messageService.getSeq(Constance.KEY_OES_SEQ).intValue();
        request.setSeq(seq);
        response.setSeq(seq);
        log.debug("生成并设置request, response的seq为oes系统维护的唯一seq: {}", seq);

        try {
            // 执行处理
            joinPoint.proceed(args);
        } catch (OesException e) {
            throw e;
        } catch (Throwable e) {
            log.error("aop处理中出现异常, request: {}, \ne: {}", request, e.getMessage(), e);
            throw e;
        } finally {
            // 设置request的seq为原来的老值
            request.setSeq(oldSeq);
            log.debug("设置回request的seq: {}", oldSeq);
        }

        return null;
    }
}

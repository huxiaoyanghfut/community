package com.nowcoder.community.controller.advice;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author:xiaoyang
 * @Title: ExceptionAdvice
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/22 20:30
 */
//首先使用@ControllerAdvice注解修饰类，指定范围是controller下的类
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {
    //声明日志
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    //写异常处理的方法，直接捕获所有异常
    @ExceptionHandler({Exception.class})
    public void handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //记录日志
        logger.error("服务器发送异常：" + e.getMessage());
        //栈追溯出错误的地方
        for (StackTraceElement element : e.getStackTrace()) {
            logger.error(element.toString());
        }
        //如果是异步请求，返回json格式消息
        String xRequestWith = request.getHeader("x-requested-with");
        //如果是异步请求，返回json格式消息
        if ("XMLHttpRequest".equals(xRequestWith)){
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1, "服务器异常"));
        }else{ //如果是普通请求，重定向至404页面
            response.sendRedirect(request.getContextPath() + "/error");

        }

    }
}

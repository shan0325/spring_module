package com.sm.app.admin.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (log.isDebugEnabled()) {
            log.debug("======================================          START         ======================================");
            log.debug(" Request URI : {}", request.getRequestURI());
            log.debug(" Request Parameter : {}", getParameterValues(request.getParameterMap()));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (log.isDebugEnabled()) {
            log.debug("======================================           END          ======================================\n");
        }
    }

    private String getParameterValues(Map<String, String[]> parameterMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();

            sb.append("\n");
            sb.append(key);
            sb.append(" : ");
            if (values.length == 1) {
                sb.append(values[0]);
            } else {
                sb.append(Arrays.toString(values));
            }
        }
        return sb.toString();
    }
}

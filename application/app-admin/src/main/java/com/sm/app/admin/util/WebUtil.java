package com.sm.app.admin.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;


public class WebUtil {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String contentType = StringUtils.defaultString(request.getHeader("Content-Type"));
        String xRequestedWith = StringUtils.defaultString(request.getHeader("X-Requested-With"));
        String isAjax = StringUtils.defaultString(request.getHeader("IS_AJAX"), "N");

        return contentType.contains(MediaType.APPLICATION_JSON_VALUE) || "XMLHttpRequest".equals(xRequestedWith) || "Y".equals(isAjax);
    }
}

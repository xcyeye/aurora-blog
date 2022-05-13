package xyz.xcye.aurora.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.core.util.LogUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 09:38
 */

@Component
public class AuroraRequestUtils {

    public Map<String,String> getRequestHeadsFromHolder() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        Map<String,String> headMaps = new HashMap<>();

        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        while (requestHeaderNames.hasMoreElements()) {
            String headName = requestHeaderNames.nextElement();
            String headerValue = request.getHeader(headName);

            // 将请求头名称和值放入map中
            headMaps.put(headName, headerValue);
        }

        return headMaps;
    }

    public static boolean getWhiteUrlFlag() {
        RequestAttributes currentRequestAttributes = RequestContextHolder.currentRequestAttributes();
        String whiteUrlStatus = null;
        try {
            whiteUrlStatus = (String) currentRequestAttributes.getAttribute(RequestConstant.CONTEXT_WHITE_URL_STATUS, 1);
        } catch (Exception e) {
            LogUtils.logExceptionInfo(e);
            return false;
        }
        return "true".equals(whiteUrlStatus);
    }
}

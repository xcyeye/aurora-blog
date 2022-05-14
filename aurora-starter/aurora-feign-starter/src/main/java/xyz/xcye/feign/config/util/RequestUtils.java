package xyz.xcye.feign.config.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.feign.config.request.AuroraRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 09:38
 */

@Component
public class RequestUtils {
    public Map<String,String> getRequestHeadsFromHolder() {

        if (RequestContextHolder.getRequestAttributes() instanceof AuroraRequestAttributes) {
            return getAllFromAuroraRequestAttributes();
        }

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

    /**
     * 获取需要的请求头，也就是必须要的，比如白名单标识等
     * @return
     */
    public Map<String, String> getAllNeedHeaders() {
        if (RequestContextHolder.getRequestAttributes() instanceof AuroraRequestAttributes) {
            return getAllFromAuroraRequestAttributes();
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        // 获取需要的请求头信息
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取请求头中的白名单状态
        String whiteUrlStatus = (String) request.getAttribute(RequestConstant.REQUEST_WHITE_URL_STATUS);

        // 获取存储用户认证信息
        String jwtUserInfoBase64 = (String) request.getAttribute(RequestConstant.REQUEST_TOKEN_NAME);
        Map<String,String> headMaps = new HashMap<>();
        /*if (StringUtils.hasLength(whiteUrlStatus)) {
            headMaps.put()
        }*/

        return null;

    }

    private Map<String, String> getAllFromAuroraRequestAttributes() {
        AuroraRequestAttributes auroraRequestAttributes = (AuroraRequestAttributes) RequestContextHolder.getRequestAttributes();
        Map<String, Object> allHeaderMap = auroraRequestAttributes.getAllHeaderMap();
        Map<String,String> map = new HashMap<>();
        allHeaderMap.forEach((key,value) -> map.put(key, value + ""));
        return map;
    }
}

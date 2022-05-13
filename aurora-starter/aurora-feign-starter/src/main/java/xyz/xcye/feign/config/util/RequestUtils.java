package xyz.xcye.feign.config.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
            AuroraRequestAttributes auroraRequestAttributes = (AuroraRequestAttributes) RequestContextHolder.getRequestAttributes();
            Map<String, Object> allHeaderMap = auroraRequestAttributes.getAllHeaderMap();
            Map<String,String> map = new HashMap<>();
            allHeaderMap.forEach((key,value) -> map.put(key, value + ""));
            return map;
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
}

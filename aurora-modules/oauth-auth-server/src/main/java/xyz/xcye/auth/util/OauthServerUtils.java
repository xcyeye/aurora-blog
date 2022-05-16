package xyz.xcye.auth.util;

import org.springframework.http.MediaType;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.util.ConvertObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author qsyyke
 * @date Created in 2022/5/8 09:10
 */


public class OauthServerUtils {
    public static void success(HttpServletRequest request, HttpServletResponse response, Object data) throws IOException {
        setHeader(response);
        R r = R.success(ResponseStatusCodeEnum.SUCCESS.getCode(), ResponseStatusCodeEnum.SUCCESS.getMessage(), data, true);
        writeJson(response, r);
    }

    public static void failure(HttpServletRequest request, HttpServletResponse response, String message, Integer code) throws IOException {
        setHeader(response);
        R r = R.failure(code, message);
        writeJson(response, r);
    }

    public static void setHeader(HttpServletResponse response) {
        //设置响应头
        MediaType applicationJson = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
        response.setContentType(applicationJson.toString());
    }

    public static void writeJson(HttpServletResponse response, R r) throws IOException {
        String json = ConvertObjectUtils.jsonToString(r);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}

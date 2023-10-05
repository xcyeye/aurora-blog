package xyz.xcye.auth.util;

import lombok.Data;
import org.lionsoul.ip2region.xdb.Searcher;
import xyz.xcye.core.exception.login.LoginException;
import xyz.xcye.core.util.lambda.AssertUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xcye
 * @description
 * @date 2023-02-13 20:27:27
 */

public class Ip2RegionUtils {

    @Data
    static class IpInfo {
        private String country;
        private String region;
        private String province;
        private String city;
        private String isp;
    }

    public static IpInfo getIpLocationInfo(String ip) throws Exception {
        InputStream stream = Ip2RegionUtils.class.getResourceAsStream("/ip2region.xdb");
        AssertUtils.stateThrow(stream != null, () -> new IOException("没有ip2region.xdb文件"));
        Searcher searcher = Searcher.newWithBuffer(stream.readAllBytes());
        String region = searcher.search(ip);
        String[] ipRegionArr = region.split("\\|");
        if (ipRegionArr.length != 5) {
            throw new LoginException("解析ip信息错误");
        }
        IpInfo ipInfo = new IpInfo();
        ipInfo.setCountry(ipRegionArr[0]);
        ipInfo.setRegion(ipRegionArr[1]);
        ipInfo.setProvince(ipRegionArr[2]);
        ipInfo.setCity(ipRegionArr[3]);
        ipInfo.setIsp(ipRegionArr[4]);
        return ipInfo;
    }

    public static String getIpLocation(String ip) throws Exception {
        StringBuilder builder = new StringBuilder();
        IpInfo ipLocationInfo = getIpLocationInfo(ip);
        if (!"0".equals(ipLocationInfo.getCountry())) {
            builder.append(ipLocationInfo.getCountry());
        }
        if (!"0".equals(ipLocationInfo.getProvince())) {
            builder.append(ipLocationInfo.getProvince());
        }
        if (!"0".equals(ipLocationInfo.getCity())) {
            builder.append(ipLocationInfo.getCity());
        }
        return builder.toString();
    }

}

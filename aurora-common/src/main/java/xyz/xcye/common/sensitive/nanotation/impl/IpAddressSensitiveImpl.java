package xyz.xcye.common.sensitive.nanotation.impl;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author qsyyke
 */

@AllArgsConstructor
public class IpAddressSensitiveImpl extends AbstractInfoSensitiveImpl {
    @Override
    public String convert() {
        String ipAddressTemp = "";
        String[] ipSingleArray = this.getCensorInfo().split("\\.");

        for (int i = 0; i < ipSingleArray.length; i++) {
            //ip地址的地2，3位需要隐藏
            if (i == 1 || i == 2) {
                ipAddressTemp = ipAddressTemp + this.getConvertSensitiveStr() + ".";
            }else {
                ipAddressTemp = ipAddressTemp + ipSingleArray[i] + ".";
            }
        }
        return ipAddressTemp.substring(0,ipAddressTemp.length() -1);
    }


    public IpAddressSensitiveImpl(String info, List<String> sensitiveList) {
        super(info, sensitiveList);
    }
}

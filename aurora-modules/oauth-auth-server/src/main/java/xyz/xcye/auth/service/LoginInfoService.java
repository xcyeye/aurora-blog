package xyz.xcye.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.auth.po.LoginInfo;
import xyz.xcye.auth.pojo.LoginInfoPojo;
import xyz.xcye.auth.vo.LoginInfoVO;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.NetWorkUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 23:10
 */

@Service
public class LoginInfoService {

    @Autowired
    private AuroraLoginInfoService auroraLoginInfoService;

    public int deleteByPrimaryKey(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return auroraLoginInfoService.deleteById(uid);
    }

    public int deleteByUidBatch(Long[] uids) {
        Assert.notNull(uids, "批量删除记录，uids不能为null");
        final int[] successDeleteNum = {0};
        Arrays.stream(uids).forEach(uid -> {
            successDeleteNum[0] = successDeleteNum[0] + auroraLoginInfoService.deleteById(uid);
        });
        return successDeleteNum[0];
    }

    public void insertSelective(LoginInfoPojo record) {
        Assert.notNull(record, "登录信息不能为null");
        record.setStatus(Optional.ofNullable(record.getStatus()).orElse(false));
        auroraLoginInfoService.insert(BeanUtils.copyProperties(record, LoginInfo.class));
    }

    public PageData<LoginInfoVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition, t -> auroraLoginInfoService.queryListByCondition(condition), LoginInfoVO.class);
    }

    public LoginInfoVO selectByUsername(String username) {
        // 返回该用户最近登录的日志信息
        Condition<Long> condition = Condition.instant(username);
        condition.setOrderBy("create_time");
        List<LoginInfoVO> loginInfoVOList = PageUtils.pageList(condition,
                t -> auroraLoginInfoService.queryListByCondition(condition), LoginInfoVO.class).getResult();
        return BeanUtils.getSingleObjFromList(loginInfoVOList, LoginInfoVO.class);
    }

    public int updateByPrimaryKeySelective(LoginInfoPojo record) {
        Assert.notNull(record, "登录信息不能为null");
        // 如果ip为null的话，则从请求中获取
        if (!StringUtils.hasLength(record.getLoginIp())) {
            String ip = NetWorkUtils.getIpAddr(AuroraRequestUtils.getCurrentRequest());
            record.setLoginIp(ip);
        }
        return auroraLoginInfoService.updateById(BeanUtils.copyProperties(record, LoginInfo.class));
    }
}

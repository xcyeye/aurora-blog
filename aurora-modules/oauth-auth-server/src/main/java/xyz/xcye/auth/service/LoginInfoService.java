package xyz.xcye.auth.service;

import xyz.xcye.auth.po.LoginInfo;
import xyz.xcye.auth.vo.LoginInfoVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 23:09
 */

public interface LoginInfoService {

    int deleteByPrimaryKey(Long uid);

    int deleteByUidBatch(Long[] uids);

    int insertSelective(LoginInfo record);

    PageData<LoginInfoVO> selectByCondition(Condition<Long> condition);

    LoginInfoVO selectByUsername(String username);

    int updateByPrimaryKeySelective(LoginInfo record);
}

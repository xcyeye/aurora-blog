package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.xcye.admin.po.Setting;
import xyz.xcye.admin.pojo.SettingPojo;
import xyz.xcye.admin.vo.SettingVO;
import xyz.xcye.core.exception.setting.SettingException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 21:04
 */

@Service
public class SettingService {

    @Autowired
    private AuroraSettingService auroraSettingService;

    @Transactional
    public void insertSysSetting(SettingPojo pojo) {
        Assert.notNull(pojo, "系统信息不能为null");
        // 查看此角色是否存在
        Setting setting = auroraSettingService.queryOne(new Setting() {{
            setParamName(pojo.getParamName());
        }});
        AssertUtils.stateThrow(setting != null, () -> new SettingException("数据库中存在相同名称"));
        auroraSettingService.insert(BeanUtils.copyProperties(pojo, Setting.class));
    }

    public int updateSysSetting(SettingPojo pojo) {
        // 判断此参数名是否存在
        Setting setting = auroraSettingService.queryOne(new Setting() {{
            setParamName(pojo.getParamName());
        }});
        if (setting != null && !setting.getUid().equals(pojo.getUid())) {
            throw new SettingException("存在相似参数名");
        }
        return auroraSettingService.updateById(BeanUtils.copyProperties(pojo, Setting.class));
    }

    public int physicalDeleteSysSetting(long uid) {
        return auroraSettingService.deleteById(uid);
    }

    public PageData<SettingVO> queryListSysSettingByCondition(Condition<Long> condition) {
        return PageUtils.copyPageDataResult(auroraSettingService.queryListByCondition(condition), SettingVO.class);
    }

    public SettingVO querySysSettingByUid(long uid) {
        return BeanUtils.copyProperties(auroraSettingService.queryById(uid), SettingVO.class);
    }
}

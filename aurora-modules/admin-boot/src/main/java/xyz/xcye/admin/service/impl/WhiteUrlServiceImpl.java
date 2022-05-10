package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.WhiteUrlMapper;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.admin.service.WhiteUrlService;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author qsyyke
 * @date Created in 2022/5/10 21:11
 */

@Service
public class WhiteUrlServiceImpl implements WhiteUrlService {

    @Autowired
    private WhiteUrlMapper whiteUrlMapper;

    @Override
    public int deleteByUid(Long uid) {
        return whiteUrlMapper.deleteByUid(uid);
    }

    @Override
    public int insert(WhiteUrl record) {
        Objects.requireNonNull(record, "白名单记录不能为null");
        Optional.ofNullable(record.getUrl()).orElseThrow(() -> new NullPointerException("白名单地址不能为null"));
        return whiteUrlMapper.insert(record);
    }

    @Override
    public int insertSelective(WhiteUrl record) {
        Objects.requireNonNull(record, "白名单记录不能为null");
        Optional.ofNullable(record.getUrl()).orElseThrow(() -> new NullPointerException("白名单地址不能为null"));
        return whiteUrlMapper.insertSelective(record);
    }

    @Override
    public PageData<WhiteUrl> selectByCondition(Condition<Integer> condition) {
        return PageUtils.pageList(condition, t -> whiteUrlMapper.selectByCondition(condition));
    }

    @Override
    public int updateByPrimaryKeySelective(WhiteUrl record) {
        record.setUpdateTime(DateUtils.format());
        return whiteUrlMapper.updateByPrimaryKeySelective(record);
    }
}

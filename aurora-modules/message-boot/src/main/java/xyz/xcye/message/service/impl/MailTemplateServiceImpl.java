package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.mybatis.entity.Condition;
import xyz.xcye.mybatis.entity.PageData;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.PageUtils;
import xyz.xcye.message.dao.MailTemplateDao;
import xyz.xcye.message.po.MailTemplate;
import xyz.xcye.message.service.MailTemplateService;
import xyz.xcye.message.vo.MailTemplateVO;

/**
 * @author qsyyke
 * @date Created in 2022/4/27 12:33
 */

@Service
public class MailTemplateServiceImpl implements MailTemplateService {

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Override
    public int insertMailTemplate(MailTemplate mailTemplate) {
        mailTemplate.setCreateTime(DateUtils.format());
        return mailTemplateDao.insertMailTemplate(mailTemplate);
    }

    @Override
    public int updateMailTemplate(MailTemplate mailTemplate) {
        mailTemplate.setUpdateTime(DateUtils.format());
        return mailTemplateDao.updateMailTemplate(mailTemplate);
    }

    @Override
    public int deleteMailTemplate(long uid) {
        return mailTemplateDao.deleteMailTemplate(uid);
    }

    @Override
    public PageData<MailTemplateVO> queryAllMailTemplate(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), condition.getOrderBy());
        return PageUtils.pageList(BeanUtils.copyList(mailTemplateDao.queryAllMailTemplate(condition), MailTemplateVO.class));
    }

    @Override
    public MailTemplateVO queryMailTemplateByUid(long uid) {
        Condition<Long> condition = Condition.instant(uid, Long.class, true);
        return BeanUtils.getSingleObjFromList(mailTemplateDao.queryAllMailTemplate(condition), MailTemplateVO.class);
    }

    @Override
    public MailTemplateVO queryMailTemplateByUserUid(long userUid) {
        Condition<Long> condition = Condition.instant(userUid, Long.class, false);
        return BeanUtils.getSingleObjFromList(mailTemplateDao.queryAllMailTemplate(condition), MailTemplateVO.class);
    }

    private String getCorrectReplaceArrStr(String replaceArrStr) {
        String[] splitArr = replaceArrStr.split(",");
        StringBuilder builder = new StringBuilder();
        for (String splitStr : splitArr) {
            builder.append(splitStr);
            builder.append(",");
        }

        return builder.length() > 0 ? builder.substring(0,builder.length() -1) : builder.toString();
    }
}

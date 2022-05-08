package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.JSONUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.mail.api.feign.UserFeignService;
import xyz.xcye.message.dao.EmailDao;
import xyz.xcye.message.po.Email;
import xyz.xcye.message.service.EmailService;
import xyz.xcye.message.vo.EmailVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author qsyyke
 */

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private AuroraProperties auroraProperties;

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public int insertEmail(Email email)
            throws BindException, AuroraException {
        // 判断邮箱是否已经存在
        if (queryByEmail(email.getEmail()) != null) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_EXISTS);
        }

        R userR = userFeignService.queryUserByUid(email.getUserUid());
        UserVO userVO = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(userR), "data", UserVO.class);

        if (userVO == null || userVO.getUid() == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST);
        }

        // 判断是否绑定 如果用户没有验证邮箱，也重新绑定
        if (userVO.getVerifyEmail()) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_HAD_BINDING);
        }

        //生成一个uid
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(), auroraProperties.getSnowFlakeDatacenterId());
        //其中user_uid应该在调用的此方法的时候，就已经赋值在email对象里面
        email.setUid(uid);
        email.setCreateTime(DateUtils.format(new Date()));
        return emailDao.insertEmail(email);
    }

    @Override
    public int deleteEmailByUid(long uid) {
        return emailDao.deleteEmailByUid(uid);
    }

    @Override
    public int updateEmail(Email email) {
        Objects.requireNonNull(email,"修改操作需要传入数据");
        email.setUpdateTime(DateUtils.format(new Date()));
        return emailDao.updateEmail(email);
    }

    @Override
    public PageData<EmailVO> queryAllEmail(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), condition.getOrderBy());
        return PageUtils.pageList(condition, t -> BeanUtils.copyList(emailDao.queryAllEmail(condition), EmailVO.class));
    }

    @Override
    public EmailVO queryByUid(long uid) {
        Condition<Long> condition = new Condition<>();
        condition.setUid(uid);
        return BeanUtils.getSingleObjFromList(emailDao.queryAllEmail(condition),EmailVO.class);
    }

    @Override
    public EmailVO queryByUserUid(long userUid) {
        Condition<Long> condition = new Condition<>();
        condition.setOtherUid(userUid);
        return BeanUtils.getSingleObjFromList(emailDao.queryAllEmail(condition), EmailVO.class);
    }

    @Override
    public EmailVO queryByEmail(String email) {
        Condition<Long> condition = new Condition();
        condition.setKeyword(email);
        return BeanUtils.getSingleObjFromList(emailDao.queryAllEmail(condition), EmailVO.class);
    }
}

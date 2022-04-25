package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.exception.user.UserException;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.JSONUtils;
import xyz.xcye.common.util.ObjectConvertJson;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.vo.EmailVO;
import xyz.xcye.common.vo.UserVO;
import xyz.xcye.message.dao.EmailDao;
import xyz.xcye.message.feign.UserFeignService;
import xyz.xcye.message.manager.mq.send.OperateUserSendService;
import xyz.xcye.message.service.EmailService;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class EmailServiceImpl implements EmailService {

    private final static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private EmailDao emailDao;

    /**
     * 当前机器的id
     */
    @Value("${aurora.snow-flake.workerId}")
    private int workerId;

    /**
     * 该台机器对应的数据中心id
     */
    @Value("${aurora.snow-flake.datacenterId}")
    private int datacenterId;

    @Autowired
    private UserFeignService userFeignService;

    @GlobalTransactional
    @Override
    public ModifyResult insertEmail(EmailDO email) throws EmailException, InstantiationException, IllegalAccessException, UserException, BindException {
        // 判断邮箱是否已经存在
        if (queryByEmail(email.getEmail()) != null) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_EXISTS.getMessage(),
                    ResponseStatusCodeEnum.EXCEPTION_EMAIL_EXISTS.getCode());
        }

        R userR = userFeignService.queryUserByUid(email.getUserUid());
        UserVO userVO = JSONUtils.parseObjFromResult(ObjectConvertJson.jsonToString(userR), "data", UserVO.class);

        if (userVO == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST.getMessage(),
                    ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST.getCode());
        }

        // 判断是否绑定
        if (userVO.getEmailUid() != null) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_HAD_BINDING.getMessage(),
                    ResponseStatusCodeEnum.EXCEPTION_EMAIL_HAD_BINDING.getCode());
        }

        //生成一个uid
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);
        //其中user_uid应该在调用的此方法的时候，就已经赋值在email对象里面
        email.setUid(uid);
        email.setCreateTime(DateUtils.format(new Date()));
        int insertEmailNum = emailDao.insertEmail(email);

        logger.info("插入新邮箱记录: {},结果{}",email,insertEmailNum);
        // 绑定邮箱
        R r = userFeignService.bindingEmail(email);
        ModifyResult modifyResult = JSONUtils.parseObjFromResult(ObjectConvertJson.jsonToString(r), "data", ModifyResult.class);

        logger.info("feign绑定邮箱,email的uid:{},绑定结果:{}",email.getUid(),modifyResult);
        if (!modifyResult.isSuccess()) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_FAIL_BINDING.getMessage(),
                    ResponseStatusCodeEnum.EXCEPTION_EMAIL_FAIL_BINDING.getCode());
        }
        return ModifyResult.operateResult(insertEmailNum,"插入email数据",
                 ResponseStatusCodeEnum.SUCCESS.getCode(),email.getUid());
    }

    @Override
    public ModifyResult deleteEmailByUid(long uid) {
        //验证uid是否有效
        if (uid == 0) {
            return ModifyResult.operateResult(ResponseStatusCodeEnum.PARAM_IS_INVALID.getMessage(),
                    0,ResponseStatusCodeEnum.PARAM_IS_INVALID.getCode(),uid);
        }
        //删除
        int deleteEmailNum = emailDao.deleteEmailByUid(uid);
        return ModifyResult.operateResult(deleteEmailNum,"删除" + uid + "对应的email数据",
                ResponseStatusCodeEnum.SUCCESS.getCode(),uid);
    }

    @Override
    public ModifyResult updateEmail(EmailDO email) {
        //判断uid是否有效
        if (email == null) {
            return ModifyResult.operateResult(ResponseStatusCodeEnum.PARAM_IS_INVALID.getMessage(),
                    0,ResponseStatusCodeEnum.PARAM_IS_INVALID.getCode(), email.getUid());
        }
        email.setUpdateTime(DateUtils.format(new Date()));
        int updateEmailByUidNum = emailDao.updateEmail(email);
        return ModifyResult.operateResult(updateEmailByUidNum,"修改" + email.getUid() + "对应的email数据",
                ResponseStatusCodeEnum.SUCCESS.getCode(), email.getUid());
    }

    @Override
    public List<EmailVO> queryAllEmail(ConditionDTO condition) throws InstantiationException, IllegalAccessException {
        condition = ConditionDTO.init(condition);
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return BeanUtils.copyList(emailDao.queryAllEmail(condition), EmailVO.class);
    }

    @Override
    public EmailVO queryByUid(long uid) throws InstantiationException, IllegalAccessException {
        ConditionDTO<Long> condition = new ConditionDTO<>();
        condition.setUid(uid);
        return BeanUtils.getSingleObjFromList(emailDao.queryAllEmail(condition),EmailVO.class);
    }

    @Override
    public EmailVO queryByUserUid(long userUid) throws InstantiationException, IllegalAccessException {
        ConditionDTO<Long> conditionDTO = new ConditionDTO<>();
        conditionDTO.setOtherUid(userUid);
        return BeanUtils.getSingleObjFromList(emailDao.queryAllEmail(conditionDTO), EmailVO.class);
    }

    @Override
    public EmailVO queryByEmail(String email) throws InstantiationException, IllegalAccessException {
        ConditionDTO<Long> conditionDTO = new ConditionDTO();
        conditionDTO.setKeyword(email);
        return BeanUtils.getSingleObjFromList(emailDao.queryAllEmail(conditionDTO), EmailVO.class);
    }
}

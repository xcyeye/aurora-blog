package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.message.dao.EmailDao;
import xyz.xcye.message.service.EmailService;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class EmailServiceImpl implements EmailService {

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

    /**
     * 查询时默认的初始页数
     */
    @Value("${aurora.pagination.pageNum}")
    private int defaultPageNum;

    /**
     * 查询时默认的返回数目
     */
    @Value("${aurora.pagination.pageSize}")
    private int defaultPageSize;

    @Override
    public ModifyResult insertEmail(EmailDO email) {
        //生成一个uid
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);

        //其中user_uid应该在调用的此方法的时候，就已经赋值在email对象里面
        email.setUid(uid);
        email.setCreateTime(DateUtils.format(new Date()));
        int insertEmailNum = emailDao.insertEmail(email);
        return ModifyResult.operateResult(insertEmailNum,"插入email数据",
                 ResponseStatusCodeEnum.SUCCESS.getCode());
    }

    @Override
    public ModifyResult deleteEmailByUid(long uid) {
        //验证uid是否有效
        if (uid == 0) {
            return ModifyResult.operateResult(ResponseStatusCodeEnum.PARAM_IS_INVALID.getMessage(),
                    0,ResponseStatusCodeEnum.PARAM_IS_INVALID.getCode());
        }

        //删除
        int deleteEmailNum = emailDao.deleteEmailByUid(uid);
        return ModifyResult.operateResult(deleteEmailNum,"删除" + uid + "对应的email数据",
                ResponseStatusCodeEnum.SUCCESS.getCode());
    }

    @Override
    public ModifyResult updateDeleteStatus(EmailDO email) {
        //验证uid是否有效
        if (email.getUid() == 0) {
            return ModifyResult.operateResult(ResponseStatusCodeEnum.PARAM_IS_INVALID.getMessage(),
                    0,ResponseStatusCodeEnum.PARAM_IS_INVALID.getCode());
        }
        email.setUpdateTime(DateUtils.format(new Date()));
        int updateDeleteStatus = emailDao.updateDeleteStatus(email);
        return ModifyResult.operateResult(updateDeleteStatus,"修改" + email.getUid() + "对应的email数据",
                ResponseStatusCodeEnum.SUCCESS.getCode());
    }

    @Override
    public ModifyResult updateEmailByUid(EmailDO email) {
        //判断uid是否有效
        if (email == null) {
            return ModifyResult.operateResult(ResponseStatusCodeEnum.PARAM_IS_INVALID.getMessage(),
                    0,ResponseStatusCodeEnum.PARAM_IS_INVALID.getCode());
        }
        email.setUpdateTime(DateUtils.format(new Date()));
        int updateEmailByUidNum = emailDao.updateEmailByUid(email);

        //根据uid查询修改之后的email
        EmailDO queryEmail = queryByUid(email.getUid());
        return ModifyResult.operateResult(updateEmailByUidNum,"修改" + email.getUid() + "对应的email数据",
                ResponseStatusCodeEnum.SUCCESS.getCode());
    }

    @Override
    public List<EmailDO> queryAllEmail(EmailDO email, PaginationDTO pagination) {
        pagination = PaginationDTO.initPagination(pagination,defaultPageNum,defaultPageSize);
        PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize(),pagination.getOrderBy());
        List<EmailDO> emails = emailDao.queryAllEmail(email);
        PageInfo<EmailDO> filePageInfo = new PageInfo<>(emails);
        return filePageInfo.getList();
    }

    @Override
    public EmailDO queryByUid(long uid) {
        return emailDao.queryByUid(uid);
    }

    @Override
    public EmailDO queryByUserUid(long userUid) {
        return emailDao.queryByUserUid(userUid);
    }
}

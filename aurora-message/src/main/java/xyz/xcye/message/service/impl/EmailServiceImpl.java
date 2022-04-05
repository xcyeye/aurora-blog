package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.Email;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.common.util.NameUtil;
import xyz.xcye.common.util.id.GenerateInfoUtil;
import xyz.xcye.message.dao.EmailDao;
import xyz.xcye.message.service.EmailService;

import javax.annotation.Resource;
import java.math.BigInteger;
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
    public ModifyResult insertEmail(Email email) {
        //生成一个uid
        long uid = GenerateInfoUtil.generateUid(workerId, datacenterId);

        //其中user_uid应该在调用的此方法的时候，就已经赋值在email对象里面
        email.setUid(new BigInteger(uid + ""));
        int insertEmailNum = emailDao.insertEmail(email);

        String msg = insertEmailNum == 1 ? "添加成功" : "添加失败";
        return new ModifyResult(insertEmailNum,insertEmailNum == 1,msg,insertEmailNum == 1 ? email : null);
    }

    @Override
    public ModifyResult deleteEmailByUid(Email email) {

        //验证uid是否有效
        if (email.getUid() == null) {
            return new ModifyResult(0,false,"uid不能为null",null);
        }

        //删除
        int deleteEmailNum = emailDao.deleteEmailByUid(email);

        String msg = deleteEmailNum == 1 ? "删除成功" : "删除失败（没有该记录或者出现了错误）";

        return new ModifyResult(deleteEmailNum,deleteEmailNum == 1,msg,deleteEmailNum == 1 ? email : null);
    }

    @Override
    public ModifyResult updateEmailByUid(Email email) {
        //判断uid是否有效
        if (email == null) {
            return new ModifyResult(0,false,"uid不能为null",null);
        }

        int updateEmailByUidNum = emailDao.updateEmailByUid(email);

        String msg = updateEmailByUidNum == 1 ? "修改成功" : "修改失败";

        //根据uid查询修改之后的email
        Email queryEmail = queryByUid(email.getUid()+"");

        return new ModifyResult(updateEmailByUidNum,updateEmailByUidNum == 1,msg,updateEmailByUidNum == 1 ? queryEmail : null);
    }

    @Override
    public List<Email> queryAllEmail(Email email, Pagination pagination) {

        pagination = Pagination.initPagination(pagination,defaultPageNum,defaultPageSize);

        PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize(),pagination.getOrderBy());
        List<Email> emails = emailDao.queryAllEmail(email);
        PageInfo<Email> filePageInfo = new PageInfo<>(emails);
        return filePageInfo.getList();
    }

    @Override
    public Email queryByUid(String uid) {
        return emailDao.queryByUid(uid);
    }
}

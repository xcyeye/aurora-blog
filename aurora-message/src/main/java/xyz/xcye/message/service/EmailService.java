package xyz.xcye.message.service;

import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.Email;

import java.util.List;

/**
 * 操作数据库中的au_email表
 * @author qsyyke
 */

public interface EmailService {
    /**
     * 向数据库中插入一条email
     * @param email email对象
     * @return
     */
    ModifyResult insertEmail(Email email);

    /**
     * 根据uid删除一条记录
     * @param email
     * @return
     */
    ModifyResult deleteEmailByUid(Email email);

    /**
     * 根据uid修改记录 修改的内容在email对象中
     */
    ModifyResult updateEmailByUid(Email email);

    /**
     * 根据自定义条件查询对应的内容
     * @param email 存放查询条件
     * @param pagination 分页条件
     * @return
     */
    List<Email> queryAllEmail(Email email, Pagination pagination);

    /**
     * 通过uid进行查询
     * @param uid
     * @return
     */
    Email queryByUid(String uid);
}

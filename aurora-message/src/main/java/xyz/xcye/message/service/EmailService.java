package xyz.xcye.message.service;

import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;

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
    ModifyResult insertEmail(EmailDO email);

    /**
     * 根据uid删除一条记录
     * @param uid
     * @return
     */
    ModifyResult deleteEmailByUid(long uid);

    /**
     * 修改email.uid对应的delete状态
     * @param email
     * @return
     */
    ModifyResult updateDeleteStatus(EmailDO email);

    /**
     * 根据uid修改记录 修改的内容在email对象中
     */
    ModifyResult updateEmailByUid(EmailDO email);

    /**
     * 根据自定义条件查询对应的内容
     * @param email 存放查询条件
     * @param pagination 分页条件
     * @return
     */
    List<EmailDO> queryAllEmail(EmailDO email, PaginationDTO pagination);

    /**
     * 通过uid进行查询
     * @param uid
     * @return
     */
    EmailDO queryByUid(long uid);

    /**
     * 查询数据库中，userUid这个用户所对应的邮件模板
     * @param userUid
     * @return
     */
    EmailDO queryByUserUid(@Param("userUid") long userUid);
}

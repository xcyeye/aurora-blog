package xyz.xcye.message.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.validation.BindException;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.core.exception.AuroraException;
import xyz.xcye.message.po.Email;
import xyz.xcye.message.vo.EmailVO;

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
    int insertEmail(Email email)
            throws BindException, AuroraException;

    /**
     * 根据uid删除一条记录
     * @param uid
     * @return
     */
    int deleteEmailByUid(long uid);

    /**
     * 根据uid修改记录 修改的内容在email对象中
     */
    int updateEmail(Email email);

    /**
     * 根据自定义条件查询对应的内容
     * @param condition 其中keyword对应email,otherUid对应userUid
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    PageData<EmailVO> queryAllEmail(Condition<Long> condition);

    /**
     * 通过uid进行查询
     * @param uid
     * @return
     */
    EmailVO queryByUid(long uid);

    /**
     * 查询数据库中，userUid这个用户所对应的邮件模板
     * @param userUid
     * @return
     */
    EmailVO queryByUserUid(@Param("userUid") long userUid);

    /**
     * 根据邮箱号进行查询
     * @param email
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    EmailVO queryByEmail(String email);
}

package xyz.xcye.admin.service;

import org.springframework.validation.BindException;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.mybatis.entity.Condition;
import xyz.xcye.mybatis.entity.PageData;

/**
 * 操作用户的service层
 * @author qsyyke
 */

public interface UserService {
    /**
     * 插入用户数据
     * @param user 用户的信息
     * @return
     * @throws BindException 对象属性错误
     * @throws UserException 插入，更新异常
     */
    int insertUser(User user)
            throws UserException;

    /**
     * 更新用户信息
     * @param user 需要更新的用户数据
     * @return
     * @throws BindException
     */
    int updateUser(User user) throws UserException;

    /**
     * 根据uid删除用户，同时会删除au_user_account中相关的记录
     * @param uid
     * @return
     * @throws UserException
     */
    int realDeleteByUid(long uid);

    int logicDeleteByUid(long uid);

    /**
     * 根据userDO中的信息查询所有满足的数据，没有模糊查询
     * @param condition 查询条件，其中keyword为username，status为verifAccount
     * @return
     */
    PageData<UserVO> queryAllByCondition(Condition<Long> condition);

    /**
     * 根据uid查询用户数据
     * @param uid
     * @return
     */
    UserVO queryByUid(long uid);

    User queryByUsernameContainPassword(String username);

    User queryByUidContainPassword(long uid);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    UserVO queryByUsername(String username);

    /**
     * 绑定邮箱
     * @param emailUid 邮件记录的uid
     * @return
     * @throws BindException
     */
    int bindingEmail(long emailUid) throws BindException, EmailException;
}

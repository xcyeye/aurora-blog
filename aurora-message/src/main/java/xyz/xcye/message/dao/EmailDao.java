package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.vo.UserVO;

import java.util.List;

/**
 * 操作数据库中的au_email表，dao层
 * @author qsyyke
 */

@Mapper
public interface EmailDao {

    /**
     * 插入邮箱记录
     * @param email
     * @return
     */
    int insertEmail(@Param("email") EmailDO email);

    /**
     * 根据uid删除邮箱
     * @param uid
     * @return
     */
    int deleteEmailByUid(@Param("uid") long uid);

    /**
     * 更新邮箱记录
     * @param email
     * @return
     */
    int updateEmail(@Param("email") EmailDO email);

    /**
     *
     * @param condition 其中keyword对应email,otherUid对应userUid
     * @return
     */
    List<EmailDO> queryAllEmail(@Param("condition")ConditionDTO condition);
}

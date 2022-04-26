package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.table.UserAccountDO;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface UserAccountDao {
    int insert(@Param("userAccountDO") UserAccountDO userAccountDO);

    int update(@Param("userAccountDO") UserAccountDO userAccountDO);

    int deleteByUid(@Param("uid") long uid);

    /**
     *
     * @param condition 其中otherUid为userUid，keyword为role
     * @return
     */
    List<UserAccountDO> queryAllByCondition(@Param("condition") ConditionDTO condition);
}

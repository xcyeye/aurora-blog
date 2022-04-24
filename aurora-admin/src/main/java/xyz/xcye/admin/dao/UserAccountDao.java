package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dos.UserAccountDO;
import xyz.xcye.common.dto.PaginationDTO;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface UserAccountDao {
    int insert(@Param("userAccountDO") UserAccountDO userAccountDO);

    int update(@Param("userAccountDO") UserAccountDO userAccountDO);

    int updateDeleteStatus(@Param("userAccountDO") UserAccountDO userAccountDO);

    int deleteByUid(@Param("uid") long uid);

    List<UserAccountDO> queryAll(@Param("userAccountDO") UserAccountDO userAccountDO, @Param("paginationDTO") PaginationDTO paginationDTO);

    UserAccountDO queryByUid(@Param("uid") long uid);

    UserAccountDO queryByUserUid(@Param("userUid") long userUid);
}

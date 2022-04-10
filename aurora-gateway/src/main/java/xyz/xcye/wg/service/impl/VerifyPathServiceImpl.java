package xyz.xcye.wg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.common.dos.VerifyPathDO;
import xyz.xcye.wg.dao.VerifyPathDao;
import xyz.xcye.wg.service.VerifyPathService;

import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class VerifyPathServiceImpl implements VerifyPathService {

    @Autowired
    private VerifyPathDao verifyPathDao;

    @Override
    public List<VerifyPathDO> queryAllVerifyPath() {

        List<VerifyPathDO> verifyPaths = verifyPathDao.queryAllVerifyPath();
        return verifyPaths;
    }
}

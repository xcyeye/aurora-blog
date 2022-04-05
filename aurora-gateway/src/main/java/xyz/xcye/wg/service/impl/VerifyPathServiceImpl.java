package xyz.xcye.wg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.common.entity.table.VerifyPath;
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
    public List<VerifyPath> queryAllVerifyPath() {

        List<VerifyPath> verifyPaths = verifyPathDao.queryAllVerifyPath();
        return verifyPaths;
    }
}

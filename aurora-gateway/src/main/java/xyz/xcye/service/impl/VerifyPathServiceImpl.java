package xyz.xcye.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.dao.VerifyPathDao;
import xyz.xcye.entity.table.VerifyPath;
import xyz.xcye.service.VerifyPathService;

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

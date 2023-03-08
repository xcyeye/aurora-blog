package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.po.AdminRouter;
import xyz.xcye.admin.pojo.AdminRouterPojo;
import xyz.xcye.admin.vo.AdminRouterVO;
import xyz.xcye.core.exception.permission.PermissionException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xcye
 * @description
 * @date 2022-12-30 22:38:51
 */

@Service
public class AdminRouterService {

    @Autowired
    private AuroraAdminRouterService auroraAdminRouterService;

    @Transactional(rollbackFor = Exception.class)
    public void insertAdminRouter(AdminRouterPojo pojo) {
        // 先判断数据库中是否存在 通过path进行查询
        AdminRouter queriedAdminRouter = auroraAdminRouterService.queryOne(new AdminRouter() {{
            setPath(pojo.getPath());
        }});
        AssertUtils.stateThrow(queriedAdminRouter == null, () -> new PermissionException("已存在相同路径的路由"));

        AdminRouter adminRouter = null;
        // 如果存在父路由，则尝试修改
        if (pojo.getParentRouterPathList() != null && !pojo.getParentRouterPathList().isEmpty()) {
            AdminRouter router = new AdminRouter();
            router.setPath(pojo.getParentRouterPathList().get(0));
            adminRouter = auroraAdminRouterService.queryOne(router);
            if (adminRouter == null) {
                pojo.setParentRouterUid(null);
            }
        }
        AdminRouter insert = auroraAdminRouterService.insert(BeanUtils.copyProperties(pojo, AdminRouter.class));
        if (adminRouter != null) {
            setSonAdminRouterUids(adminRouter, insert.getUid());
            // 更新
            auroraAdminRouterService.updateById(adminRouter);
        }

        // 如果该路由下存在子路由的话，也需要更新 尽管此子路由已经和其他路由绑定，但是已这次为准
        if (pojo.getSonRouterPathList() != null && !pojo.getSonRouterPathList().isEmpty()) {
            getEffectiveRouterPath(pojo.getSonRouterPathList()).forEach(v -> {
                AdminRouter sonRouter = new AdminRouter();
                sonRouter.setUid(v);
                sonRouter.setParentRouterUid(insert.getUid());
                auroraAdminRouterService.updateById(sonRouter);
            });
        }
    }

    public int updateAdminRouter(AdminRouterPojo pojo) {
        if (StringUtils.hasLength(pojo.getPath())) {
            AdminRouter queriedAdminRouter = auroraAdminRouterService.queryOne(new AdminRouter() {{
                setPath(pojo.getPath());
            }});
            if (queriedAdminRouter != null && !Objects.equals(queriedAdminRouter.getUid(), pojo.getUid())) {
                throw new PermissionException("已存在相同路径的路由");
            }
        }

        AdminRouter adminRouter = null;
        // 如果存在父路由，则尝试修改
        if (pojo.getParentRouterPathList() != null && !pojo.getParentRouterPathList().isEmpty()) {
            AdminRouter router = new AdminRouter();
            router.setPath(pojo.getParentRouterPathList().get(0));
            adminRouter = auroraAdminRouterService.queryOne(router);
            if (adminRouter == null) {
                pojo.setParentRouterUid(null);
            }
        }
        if (adminRouter != null) {
            setSonAdminRouterUids(adminRouter, pojo.getUid());
            // 更新
            pojo.setParentRouterUid(adminRouter.getUid());
            auroraAdminRouterService.updateById(adminRouter);
        }

        // 如果该路由下存在子路由的话，也需要更新 尽管此子路由已经和其他路由绑定，但是已这次为准
        if (pojo.getSonRouterPathList() != null && !pojo.getSonRouterPathList().isEmpty()) {
            String sonRouterUids = getEffectiveRouterPath(pojo.getSonRouterPathList())
                    .stream().map(v -> v + "")
                    .collect(Collectors.joining(","));
            pojo.setSonRouterUids(sonRouterUids);
            getEffectiveRouterPath(pojo.getSonRouterPathList()).forEach(v -> {
                AdminRouter sonRouter = new AdminRouter();
                sonRouter.setUid(v);
                sonRouter.setParentRouterUid(pojo.getUid());
                auroraAdminRouterService.updateById(sonRouter);
            });
        }
        return auroraAdminRouterService.updateById(BeanUtils.copyProperties(pojo, AdminRouter.class));
    }

    public int physicalDeleteAdminRouter(AdminRouterPojo pojo) {
        return auroraAdminRouterService.deleteById(pojo.getUid());
    }

    public PageData<AdminRouterVO> queryListAdminRouterByCondition(Condition<Long> condition) {
        return PageUtils.copyPageDataResult(auroraAdminRouterService.queryListByCondition(condition), AdminRouterVO.class);
    }

    public AdminRouterVO queryAdminRouterByUid(AdminRouterPojo pojo) {
        return BeanUtils.copyProperties(auroraAdminRouterService.queryById(pojo.getUid()), AdminRouterVO.class);
    }

    private boolean judgeAdminRouterUid(long adminRouterUid) {
        return auroraAdminRouterService.queryById(adminRouterUid) != null;
    }

    private List<Long> getEffectiveRouterPath(List<String> routerPathList) {
        if (routerPathList.isEmpty()) {
            return new ArrayList<>();
        }
        return routerPathList.stream().filter(v -> {
            AdminRouter router = new AdminRouter();
            router.setPath(v);
            return auroraAdminRouterService.queryOne(router) != null;
        }).map(v -> {
            AdminRouter router = new AdminRouter();
            router.setPath(v);
            return auroraAdminRouterService.queryOne(router);
        }).map(AdminRouter::getUid).collect(Collectors.toList());
    }

    private void setSonAdminRouterUids(AdminRouter parentAdminRouter, long sonAdminRouterUid) {
        if (!StringUtils.hasLength(parentAdminRouter.getSonRouterUids())) {
            parentAdminRouter.setSonRouterUids(sonAdminRouterUid + "");
            return;
        }
        String effectiveUid = Arrays.stream(parentAdminRouter.getSonRouterUids()
                        .split(","))
                .filter(uid -> auroraAdminRouterService.queryById(Long.parseLong(uid)) != null)
                .collect(Collectors.joining(","));
        parentAdminRouter.setSonRouterUids(effectiveUid + "," + sonAdminRouterUid);
    }
}

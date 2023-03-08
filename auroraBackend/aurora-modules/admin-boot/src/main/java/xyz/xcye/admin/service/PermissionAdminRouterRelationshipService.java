package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.dto.AdminRouterDTO;
import xyz.xcye.admin.po.RouterPermission;
import xyz.xcye.admin.pojo.AdminRouterPojo;
import xyz.xcye.admin.pojo.RouterPermissionPojo;
import xyz.xcye.admin.vo.AdminRouterVO;
import xyz.xcye.core.util.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xcye
 * @description
 * @date 2022-12-30 23:18:33
 */

@Service
public class PermissionAdminRouterRelationshipService {
    @Autowired
    private AuroraRouterPermissionService auroraRouterPermissionService;

    @Autowired
    private AdminRouterService adminRouterService;

    public void insertRouterPermission(RouterPermissionPojo pojo) {
        auroraRouterPermissionService.insert(BeanUtils.copyProperties(pojo, RouterPermission.class));
    }

    public int updateRouterPermission(RouterPermissionPojo pojo) {
        return auroraRouterPermissionService.updateById(BeanUtils.copyProperties(pojo, RouterPermission.class));
    }

    public int physicalDeleteRouterPermission(RouterPermissionPojo pojo) {
        return auroraRouterPermissionService.deleteById(pojo.getUid());
    }


    /**
     * 查询权限uid对应的所有路由信息
     * @param pojo
     * @return
     */
    public List<AdminRouterDTO> queryAllAdminRouterInfoByPermissionUid(RouterPermissionPojo pojo) {
        List<AdminRouterDTO> routerList = new ArrayList<>();
        pojo.getPermissionUidList().forEach(permissionUid -> {
            // 查询路由
            List<RouterPermission> routerPermissionList = auroraRouterPermissionService.queryListByWhere(new RouterPermission() {{
                setPermissionUid(permissionUid);
            }});

            routerPermissionList.forEach(router -> routerList.add(packageRouterInfo(router.getUid(), null)));
        });
        return routerList;
    }

    private AdminRouterDTO packageRouterInfo(long routerUid, AdminRouterDTO adminRouterDTO) {
        AdminRouterVO adminRouterVO = adminRouterService.queryAdminRouterByUid(new AdminRouterPojo() {{
            setUid(routerUid);
        }});
        if (adminRouterVO == null) {
            return null;
        }

        if (adminRouterDTO == null) {
            adminRouterDTO = BeanUtils.copyProperties(adminRouterVO, AdminRouterDTO.class);
        }

        List<Long> effectiveRouterUid = getEffectiveRouterUid(adminRouterVO.getSonRouterUids());
        if (adminRouterDTO.getChildren() == null && effectiveRouterUid.size() != 0) {
            adminRouterDTO.setChildren(new ArrayList<>());
        }
        for (int i = 0; i < effectiveRouterUid.size(); i++) {
            adminRouterDTO.getChildren().add(packageRouterInfo(effectiveRouterUid.get(i), adminRouterDTO));
        }
        return adminRouterDTO;
    }

    private List<Long> getEffectiveRouterUid(String sonRouterUidArr) {
        if (!StringUtils.hasLength(sonRouterUidArr)) {
            return new ArrayList<>();
        }
        return Arrays.stream(sonRouterUidArr.split(",")).map(Long::parseLong).filter(uid -> {
            AdminRouterPojo pojo = new AdminRouterPojo();
            pojo.setUid(uid);
            return adminRouterService.queryAdminRouterByUid(pojo) != null;
        }).collect(Collectors.toList());
    }
}

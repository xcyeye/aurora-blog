package xyz.xcye.admin.constant;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 20:39
 */


public class RedisStorageConstant {
    public static final String STORAGE_VERIFY_ACCOUNT_PREFIX = "verify_account_info";

    /** redis中存储所有角色名和路径权限的key **/
    public static final String STORAGE_ROLE_PERMISSION_INFO = "role_permission_info";

    /** redis中存储所有白名单的key **/
    public static final String STORAGE_WHITE_URL_INFO = "role_white_url_info";
}

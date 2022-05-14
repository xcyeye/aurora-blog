package xyz.xcye.auth.manager.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import xyz.xcye.auth.constant.AuthRedisConstant;
import xyz.xcye.auth.model.SecurityUserDetails;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.JSONUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 加载UserDetails的一个缓存
 * @author qsyyke
 * @date Created in 2022/5/14 17:21
 */

@Component
public class AuroraUserDetailsCache implements UserCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserDetails getUserFromCache(String username) {
        String json = (String) redisTemplate.opsForValue().get(AuthRedisConstant.USER_DETAILS_CACHE_PREFIX + username);
        SecurityUserDetails securityUserDetails = JSON.parseObject(json, SecurityUserDetails.class);
        ArrayList<JSONObject> grantedAuthorities = JSONUtils.parseObjFromResult(json, "grantedAuthorities", ArrayList.class);
        String[] authorities = null;
        if (grantedAuthorities != null) {
            authorities = grantedAuthorities.stream()
                .map(obj -> (String) obj.get("authority"))
                .collect(Collectors.joining(","))
                .split(",");
        }
        String[] finalAuthorities = authorities;
        Optional.ofNullable(securityUserDetails)
                .ifPresent(t -> t.setGrantedAuthorities(AuthorityUtils.createAuthorityList(finalAuthorities)));
        return securityUserDetails;
    }

    @Override
    public void putUserInCache(UserDetails user) {
        // 设置一个随机失效时间 在60分钟到5天内的随机一个数
        Duration duration = Duration.ofSeconds(DateUtils.getRandomMinute(60, 60 * 24 * 5) * 60);
        String json = ConvertObjectUtils.jsonToString(user);
        redisTemplate.opsForValue().set(AuthRedisConstant.USER_DETAILS_CACHE_PREFIX + user.getUsername(), json, duration);
    }

    @Override
    public void removeUserFromCache(String username) {
        redisTemplate.delete(AuthRedisConstant.USER_DETAILS_CACHE_PREFIX + username);
    }
}



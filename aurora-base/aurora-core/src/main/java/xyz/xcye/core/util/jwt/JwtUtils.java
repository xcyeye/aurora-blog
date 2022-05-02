package xyz.xcye.core.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.dto.JwtEntityDTO;
import xyz.xcye.core.util.id.GenerateInfoUtils;

import java.util.Date;

/**
 * 这是与jwt相关的工具类
 * @author qsyyke
 */


public class JwtUtils {

    /**
     * 生成token
     * @param subject 标题
     * @param issuer 签发者 比如管理员
     * @param expirationTime 失效时间 秒数
     * @param username 用户名
     * @param role 角色
     * @param permission 权限，多个使用,隔开
     * @param secretKey 盐
     * @return token
     */
    public static String generateToken(String subject,String issuer, long expirationTime,
                                       String username, String role,
                                       String permission, byte[] secretKey) {
        //当前时间
        Date currentDate = new Date();

        if (expirationTime == 0) {
            //默认失效时间为30分钟
            expirationTime = FieldLengthConstant.TOKEN_EXPIRATION_Time;
        }

        //将秒数转换成毫秒
        expirationTime = expirationTime * 1000;

        //失效时间
        Date expirationDate = new Date(currentDate.getTime() + expirationTime);

        return Jwts.builder()
                .setId(GenerateInfoUtils.generateUid(10, 10) + "")
                //设置标题和签发者
                .setSubject(subject).setIssuer(issuer)
                //设置发布时间和token失效时间
                .setIssuedAt(currentDate).setExpiration(expirationDate)
                //声明用户名和密码
                .claim("username", username)
                //声明用户角色和权限(多个权限使用,隔开)
                .claim("role", role).claim("permission", permission)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    /**
     * 解析传入的token
     * @param jwtToken 需要解析的token
     * @param secretKey 盐byte数组
     * @return 封装的jwt实体对象
     * @throws RuntimeException 如果token失效，会导致解析失败或者盐不匹配
     */
    public static JwtEntityDTO parseJwtToken(String jwtToken, byte[] secretKey) throws RuntimeException {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
        }catch (Exception e) {
            throw new RuntimeException("解析出错：" + e.getMessage());
        }

        JwtEntityDTO jwtEntity = new JwtEntityDTO(claims.getId(),claims.getSubject(),
                claims.getIssuer(),claims.getIssuedAt(),claims.getExpiration());
        //解析用户名
        jwtEntity.setUsername(claims.get("username",String.class));

        //解析角色
        jwtEntity.setRole(claims.get("role",String.class));

        //解析权限信息
        String[] permissions = claims.get("permission", String.class).split(",");
        jwtEntity.setPermissions(permissions);
        return jwtEntity;
    }

    /**
     * 根据传入的token和盐byte数组，判断token是否过期
     * @param jwtToken token
     * @param secretKey 盐byte数组
     * @return true为过期，false没有过期
     * @throws RuntimeException 解析失败，可能已经过期了，或者解析失败
     */
    public static boolean isExpiration(String jwtToken,byte[] secretKey) throws RuntimeException {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
        }catch (Exception e) {
            throw new RuntimeException("解析出错：" + e.getMessage());
        }

        //token失效时间
        Date expiration = claims.getExpiration();
        return !expiration.after(new Date());
    }
}

package xyz.xcye.core.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.dto.JwtEntityDTO;
import xyz.xcye.core.exception.permission.PermissionException;
import xyz.xcye.core.exception.token.TokenException;
import xyz.xcye.core.util.id.GenerateInfoUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

/**
 * 这是与jwt相关的工具类
 *
 * @author qsyyke
 */


public class JwtUtils {

    public static String generateToken(String subject, String issuer, long expirationSecond,
                                       String username, String secretKey) {
        Key key = getSecretKey(secretKey);
        // 当前时间
        Date currentDate = new Date();
        if (expirationSecond == 0) {
            // 默认失效时间为30分钟
            expirationSecond = FieldLengthConstant.TOKEN_EXPIRATION_TIME;
        }

        // 将秒数转换成毫秒
        expirationSecond = expirationSecond * 1000;

        // 失效时间
        Date expirationDate = new Date(currentDate.getTime() + expirationSecond);
        return Jwts.builder()
                .setId(GenerateInfoUtils.generateUid(10, 10) + "")
                // 设置标题和签发者
                .setSubject(subject).setIssuer(issuer)
                // 设置发布时间和token失效时间
                .setIssuedAt(currentDate).setExpiration(expirationDate)
                // 声明用户名和密码
                .claim("username", username)
                .signWith(key)
                .compact();
    }

    /**
     * 解析传入的token
     *
     * @param jwtToken  需要解析的token
     * @param secretKey 盐byte数组
     * @return 封装的jwt实体对象
     * @throws RuntimeException 如果token失效，会导致解析失败或者盐不匹配
     */
    public static JwtEntityDTO parseJwtToken(String jwtToken, String secretKey) throws RuntimeException {
        Key key = getSecretKey(secretKey);

        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e) {
            throw new TokenException(e.getMessage());
        }

        JwtEntityDTO jwtEntity = JwtEntityDTO.builder()
                .id(claims.getId()).subject(claims.getSubject())
                .issuer(claims.getIssuer())
                .issuedAt(claims.getIssuedAt())
                .expirationAt(claims.getExpiration())
                .jti((String) claims.get("jti"))
                .build();
        // 解析用户名
        jwtEntity.setUsername(claims.get("username", String.class));
        return jwtEntity;
    }

    /**
     * 根据传入的token和盐byte数组，判断token是否过期
     *
     * @param jwtToken  token
     * @param secretKey 盐
     * @return true为过期，false没有过期，就算token过期，或者解析失败，会直接返回true，并不会抛出异常
     */
    public static boolean isExpiration(String jwtToken, String secretKey) {
        Key key = getSecretKey(secretKey);

        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e) {
            return true;
        }

        // token失效时间
        Date expiration = claims.getExpiration();
        return !expiration.after(new Date());
    }

    private static Key getSecretKey(String secretKey) {
        Objects.requireNonNull(secretKey);
        // 判断secretKey的长度是否足够
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (bytes.length * 8 <= 256) {
            throw new PermissionException("secret的长度太短，存英文不能少于32位，存中文不能少于10位");
        }
        return Keys.hmacShaKeyFor(bytes);
    }
}

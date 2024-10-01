package com.ruoyi.biz.api.service;

import cn.hutool.core.date.DateTime;
import com.ruoyi.biz.domain.User;
import com.ruoyi.biz.service.IUserService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName : JwtUtil
 * @Description :
 * @Author : Fly
 * @Date: 2021-01-26 16:05
 */
@Component
public class TokenApiService {
    private static final String ISSUER = "Just API";

    public static final String SECRET_KEY = "JUST_API_1.0";

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    // 令牌有效期（默认30分钟）
    @Value("${api.token.expireTime}")
    private int expireTime;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String createToken(String id) {
        final DateTime now = DateTime.now();

        return Jwts
                .builder()
                .setSubject(id)
                .setIssuedAt(now)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public User getLoginUser(String token) {
        // 获取请求携带的令牌
        if (StringUtils.isNotEmpty(token)) {
            // 根据Token，解析出具体数据
            final Claims claims = Jwts.parser()
                    .setSigningKey(TokenApiService.SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();

            // 解析对应的权限以及用户信息
            String userKey = getTokenKey(subject);
            if (redisTemplate.hasKey(userKey)) {
                return redisCache.getCacheObject(userKey);
            }
            return null;
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(User user) {
        if (StringUtils.isNotNull(user) && StringUtils.isNotEmpty(user.getToken())) {
            refreshToken(user);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotBlank(token)) {
            // 根据Token，解析出具体数据
            final Claims claims = Jwts.parser()
                    .setSigningKey(TokenApiService.SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();
            String userKey = getTokenKey(subject);

            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param user
     * @return 令牌
     */
    public void verifyToken(User user) {
        long anchorExpireTime = user.getTokenExpiredDate().getTime();
        long currentTime = System.currentTimeMillis();
        //有效期小于1/3自动刷新token有效时间
        if (anchorExpireTime - currentTime <= (expireTime / 3 * MILLIS_MINUTE)) {
            refreshToken(user);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param anchor 登录信息
     */
    public void refreshToken(User anchor) {
        anchor.setTokenExpiredDate(new Date(System.currentTimeMillis() + expireTime * MILLIS_MINUTE));
        String userKey = getTokenKey(String.valueOf(anchor.getId()));
        redisCache.setCacheObject(userKey, anchor, expireTime, TimeUnit.MINUTES);
        User user = new User();
        user.setId(anchor.getId());
        user.setTokenExpiredDate(anchor.getTokenExpiredDate());
        userService.updateUser(user);
    }

    private String getTokenKey(String uuid) {
        return Constants.API_LOGIN_TOKEN_KEY + uuid;
    }
}

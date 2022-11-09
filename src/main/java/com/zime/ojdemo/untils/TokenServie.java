package com.zime.ojdemo.untils;


import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import com.zime.ojdemo.modle.vo.core.RedisCache;
import com.zime.ojdemo.modle.vo.core.uuid.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenServie {
    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    @Autowired
    RedisCache redisCache;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;


    public LoginUser getLoginUser(HttpServletRequest request){
        String token=getToken(request);
        System.out.println(token);
        if(token!=null&&!token.equals("undefined")) {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get("login_user_key");
            String userKey = "login_tokens:" + uuid;
            LoginUser user = redisCache.getCacheObject(userKey);
            return user;
        }
        return null;
    }

    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String createToken(LoginUser loginUser){
    String token= IdUtils.fastUUID();
    loginUser.setToken(token);
    Map<String, Object> claims = new HashMap<>();
    refreshToken(loginUser);
    claims.put("login_user_key", token);
    return createToken(claims);
    }

    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();

        return token;
    }

    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = "login_tokens:"+loginUser.getToken();
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getToken(HttpServletRequest request){
    String token=request.getHeader(header);
    return token;
    }

    public Boolean delToken(HttpServletRequest request) {
        String token = getToken(request);
        System.out.println(token);
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get("login_user_key");
            String userKey = "login_tokens:" + uuid;
        return redisCache.deleteObject(userKey);
    }
}

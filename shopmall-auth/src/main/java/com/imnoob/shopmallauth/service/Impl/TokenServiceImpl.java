package com.imnoob.shopmallauth.service.Impl;

import com.imnoob.shopmallauth.service.TokenService;
import com.imnoob.shopmallcommon.to.AdminToken;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Value("${token.secret}")
    private String secret;

    @Value(("${token.expire}"))
    private String expire;

    @Override
    public String publishToken(AdminToken o) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Map<String,Object> claims = new HashMap<String,Object>();           //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）

        claims.put("admin",o);

        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setIssuedAt(new Date(System.currentTimeMillis()))           //iat: jwt的签发时间
                .setSubject(o.getUsername())             //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, secret);  //设置签名使用的签名算法和签名使用的秘钥


        redisTemplate.opsForValue().set("token::"+o.getId(),o.getIpAddr(),Integer.parseInt(expire),TimeUnit.SECONDS);
        return builder.compact();
    }

    @Override
    public void flushToken(AdminToken adminToken) {

        redisTemplate.opsForValue().set("token::"+adminToken.getId(),adminToken.getIpAddr(),Integer.parseInt(expire),TimeUnit.SECONDS);
    }

    @Override
    public void delToken(String token) {
        AdminToken adminToken = parseToken(token);
        if (adminToken != null){
            redisTemplate.delete("token::" + adminToken.getId());
        }
    }

    @Override
    public AdminToken parseToken(String token) {
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(secret)         //设置签名的秘钥
                .parseClaimsJws(token).getBody();//设置需要解析的jwt
        AdminToken username = (AdminToken) claims.get("admin");
        return username;
    }

    @Override
    public Boolean checkToken(String token,String ip) {
        AdminToken adminToken = parseToken(token);
        if (adminToken != null){
            if (!ip.equals(adminToken.getIpAddr())) return  false;
            else
                flushToken(adminToken);
            return true;
        }else
            return false;
    }
}

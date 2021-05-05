package com.imnoob.shopmallauth.service;

import com.imnoob.shopmallcommon.to.AdminToken;

public interface TokenService {

    public String publishToken(AdminToken o);
    public void flushToken(AdminToken adminToken);
    public void delToken(String token);
    public AdminToken parseToken(String token);

    public Boolean checkToken(String token,String ip);
}

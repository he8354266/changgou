package com.changgou.oauth.service;

import com.changgou.oauth.util.AuthToken;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1414:43
 */
public interface AuthService {
    AuthToken login(String username, String password, String clientId, String clientSecret);
}

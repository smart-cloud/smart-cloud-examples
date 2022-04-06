package org.smartframework.cloud.examples.basic.user.service.api;

import org.springframework.stereotype.Service;

/**
 * token相关
 *
 * @author collin
 * @date 2019-06-29
 */
@Service
public class TokenApiService {

    /**
     * token续期
     *
     * @param token
     */
    public boolean renew(String token) {
        // TODO:token续期
        return true;
    }

}
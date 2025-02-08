package com.jaxsen.redisson;

import org.redisson.Redisson;
import org.redisson.config.Config;

/**
 * redisson 客户端
 *
 * @author mayongjie
 * @date 2025/02/08 17:28
 **/
public class RedissonClient {

    private org.redisson.api.RedissonClient redissonClient;

    public RedissonClient(String host) {
        this(host, null, null);
    }

    public RedissonClient(String host, String username, String password) {
        Config config = new Config();
        if (host == null || host.isEmpty()) {
            host = "redis://127.0.0.1:6379";
        } else if (!host.startsWith("redis://")) {
            host = "redis://" + host;
        }
        config.useSingleServer().setAddress(host);
        if (username != null && !username.isEmpty()) {
            config.useSingleServer().setUsername(username);
        }
        if (password != null && !password.isEmpty()) {
            config.useSingleServer().setPassword(password);
        }
        this.redissonClient = Redisson.create(config);
    }

    public org.redisson.api.RedissonClient getRedissonClient() {
        return redissonClient;
    }
}

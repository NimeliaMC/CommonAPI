package fr.nimelia.api.connexions.redis;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisConnexion {

    private final RedissonClient redissonClient;

    public RedisConnexion(String redisHost, int redisPort) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redisHost + ":" + redisPort);
        redissonClient = Redisson.create(config);
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    public void close() {
        if (redissonClient != null) {
            redissonClient.shutdown();
        }
    }

}

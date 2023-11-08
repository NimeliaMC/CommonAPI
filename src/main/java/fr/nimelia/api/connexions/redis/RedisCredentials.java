package fr.nimelia.api.connexions.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.provider.CloudServiceProvider;
import eu.cloudnetservice.driver.service.ServiceInfoSnapshot;
import fr.nimelia.api.CommonAPI;
import fr.nimelia.api.server.ServerInstance;
import fr.nimelia.api.server.ServerStatus;
import fr.nimelia.api.server.ServerType;
import org.redisson.api.RBuckets;
import org.redisson.api.RedissonClient;

import java.util.HashMap;
import java.util.Map;

public class RedisCredentials {

    private final RedissonClient redissonClient;
    private final ObjectMapper objectMapper;
    private final CommonAPI api;
    private final Map<String, ServerInstance> instance = new HashMap<>();

    public RedisCredentials(RedissonClient redissonClient, CommonAPI api) {
        this.redissonClient = redissonClient;
        this.objectMapper = new ObjectMapper();
        this.api = api;
        this.loadInstance();
    }

    public void loadInstance() {
        CloudServiceProvider cloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider.class);
        for (ServiceInfoSnapshot services : cloudServiceProvider.services()) {
            instance.put(services.serviceId().name(), new ServerInstance(services.serviceId().name(), services.address().port(), ServerType.fromValue(services.name().toUpperCase()), ServerStatus.PLAYING));
        }
    }

    public void saveServerInstance(ServerInstance serverInstance) {
        getBuckets().get(serverInstance.getName()).replace(serverInstance.getName(), serverInstance);
        instance.replace(serverInstance.getName(), serverInstance);
    }

    public ServerInstance getServerInstance(String serverName) {
        return instance.get(serverName);
    }

    public void deleteServerInstance(String serverName) {
        getBuckets().get(serverName).remove(serverName);
        instance.remove(serverName);
    }

    public RBuckets getBuckets() {
        return redissonClient.getBuckets();
    }
}

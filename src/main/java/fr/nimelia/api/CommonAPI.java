package fr.nimelia.api;

import com.google.common.reflect.ClassPath;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import fr.nimelia.api.account.AccountManager;
import fr.nimelia.api.commands.CommandInfo;
import fr.nimelia.api.commands.PluginCommand;
import fr.nimelia.api.connexions.mongo.MongoConnexion;
import fr.nimelia.api.connexions.redis.RedisConnexion;
import fr.nimelia.api.connexions.redis.RedisCredentials;
import fr.nimelia.api.server.ServerManager;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class CommonAPI {

    @Getter
    public static CommonAPI api;
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create();
    private final MongoConnexion mongo;
    private final RedisConnexion redis;
    private final RedisCredentials redisCredentials;
    private final AccountManager accountManager;
    private final ServerManager serverManager;
    public final String prefix = " §d§lNimelia §7➢ ";
    public final List<PluginCommand> commandList = new ArrayList<>();

    public CommonAPI() {
        if (api != null) {
            throw new RuntimeException("API already initialized");
        }
        api = this;
        this.mongo = new MongoConnexion();
        this.redis = new RedisConnexion("127.0.0.1",6379);
        this.redisCredentials = new RedisCredentials(redis.getRedissonClient(), this);
        this.accountManager = new AccountManager(this);
        this.serverManager = new ServerManager();
    }


    public static <T extends PluginCommand> void registerCommands(String packageName, Class<T> pluginClass, ClassLoader classLoader, Consumer<Class<T>> pluginCommand) throws IOException {
        ClassPath.from(classLoader)
                .getTopLevelClassesRecursive(packageName)
                .stream()
                .map(ClassPath.ClassInfo::load)
                .filter(PluginCommand.class::isAssignableFrom)
                .filter(clazz -> clazz.isAnnotationPresent(CommandInfo.class))
                .forEach(clazz -> pluginCommand.accept((Class<T>) clazz));
    }

}

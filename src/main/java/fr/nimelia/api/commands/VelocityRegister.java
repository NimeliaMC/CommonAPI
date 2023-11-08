package fr.nimelia.api.commands;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.nimelia.api.CommonAPI;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class VelocityRegister {

    private final ProxyServer server;
    private final CommonAPI common = CommonAPI.getApi();
    private final Logger logger;

    public VelocityRegister(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    public void registerCommands(String packageName) throws IOException {
        CommonAPI.registerCommands(packageName, VelocityPluginCommand.class, getClass().getClassLoader(), clazz -> {
            registerSingleCommand(newCommandInstance(clazz));
        });
    }

    private VelocityPluginCommand newCommandInstance(Class<VelocityPluginCommand> pluginCommandClass) {
        try {
            Constructor<VelocityPluginCommand> constructor = pluginCommandClass.getConstructor(ProxyServer.class);
            constructor.setAccessible(true);
            return constructor.newInstance(server);

        } catch (ReflectiveOperationException e) {
            try {
                Constructor<VelocityPluginCommand> constructor = pluginCommandClass.getConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();

            } catch (ReflectiveOperationException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    private void registerSingleCommand(VelocityPluginCommand command) {
        CommandMeta meta = server.getCommandManager().metaBuilder(command.getInfo().name())
                .aliases(command.getInfo().aliases())
                .plugin(this)
                .build();
        server.getCommandManager().register(meta, command);
        logger.info("Registered command " + command.getInfo().name() + "!");
        common.getCommandList().add(command);
    }

}

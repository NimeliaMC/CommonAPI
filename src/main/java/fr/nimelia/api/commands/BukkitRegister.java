package fr.nimelia.api.commands;

import fr.nimelia.api.CommonAPI;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class BukkitRegister {

    private final JavaPlugin bukkit;

    public BukkitRegister(JavaPlugin bukkit) {
        this.bukkit = bukkit;
    }

    public void registerCommands(String packageName) throws IOException {
        CommonAPI.registerCommands(packageName, BukkitPluginCommand.class, bukkit.getClass().getClassLoader(), clazz -> {
            registerSingleCommand(newCommandInstance(clazz));
        });
    }

    private BukkitPluginCommand newCommandInstance(Class<BukkitPluginCommand> pluginCommandClass) {
        try {
            Constructor<BukkitPluginCommand> constructor = pluginCommandClass.getConstructor(JavaPlugin.class);
            constructor.setAccessible(true);
            return constructor.newInstance(this);

        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private void registerSingleCommand(BukkitPluginCommand command) {
        try {
            ((SimpleCommandMap) bukkit.getServer().getClass().getMethod("getCommandMap").invoke(bukkit.getServer())).register(bukkit.getDescription().getName(), command);
        } catch (ReflectiveOperationException e) {
        }
    }

}

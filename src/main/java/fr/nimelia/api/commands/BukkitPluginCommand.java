package fr.nimelia.api.commands;

import com.google.common.collect.ImmutableList;
import fr.nimelia.api.common.Reflection;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public abstract class BukkitPluginCommand extends BukkitCommand implements PluginCommand<Player> {

    private final CommandInfo info;
    protected final JavaPlugin plugin;

    /**
     * This constructor is used to register the command and check if the command has the correct annotation.
     * @param plugin An instance of the plugin.
     */
    protected BukkitPluginCommand(JavaPlugin plugin) {
        super("");
        this.plugin = plugin;
        info = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(info, "CommandInfo annotation is missing");
        Reflection.setField("name", BukkitCommand.class, true, this, info.name());
        setAliases(ImmutableList.copyOf(info.aliases()));
        setDescription(info.description());
        setUsage(info.usage());
        setPermissionMessage(info.permissionMessage());
    }

    BukkitPluginCommand(JavaPlugin plugin, String name, String[] aliases, String description, String usage, String permission, String permissionMessage) {
        super("");
        this.plugin = plugin;
        Reflection.setField("name", BukkitCommand.class, true, this, name);
        setAliases(Arrays.asList(aliases));
        setDescription(description);
        setUsage(usage);
        setPermission(permission);
        setPermissionMessage(permissionMessage);
        this.info = null;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!getPermission().isEmpty() && !sender.hasPermission(getPermission())) {
            sender.sendMessage(getPermissionMessage());
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        onCommand(((Player) sender), args);
        return true;
    }

    @Override
    public @Nullable CommandInfo getInfo() {
        return info;
    }
}
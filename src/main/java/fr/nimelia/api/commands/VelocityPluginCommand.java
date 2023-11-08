package fr.nimelia.api.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.nimelia.api.CommonAPI;
import fr.nimelia.api.account.AccountManager;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class VelocityPluginCommand implements PluginCommand<SimpleCommand.Invocation>, SimpleCommand {

    private final CommandInfo info;
    protected final ProxyServer plugin;

    /**
     * This constructor is used to register the command and check if the command has the correct annotation.
     * @param plugin An instance of the plugin.
     */
    public VelocityPluginCommand(ProxyServer plugin) {
        this.plugin = plugin;
        info = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(info, "CommandInfo annotation is missing");
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        if (!(invocation.source() instanceof Player player)) return true;
        AccountManager accountManager = CommonAPI.getApi().getAccountManager();
        return accountManager.getAccount(player.getUniqueId()).getRankTypes().getWeight() >= info.permission();
    }

    @Override
    public void execute(Invocation invocation) {
        onCommand(invocation, invocation.arguments());
    }

    @Override
    public @Nullable CommandInfo getInfo() {
        return info;
    }
}
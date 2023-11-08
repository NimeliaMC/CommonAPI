package fr.nimelia.api.commands;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public interface PluginCommand<T> {


    /**
     * @return The annotation of this command. It may be null if the command has been built using the {@link CommandBuilder}
     */
    @Nullable
    public CommandInfo getInfo();

    /**
     * @param args An array of arguments passed by the command sender
     * @param index The index of the argument to get.
     * @return The argument at the given index, or an empty optional if the index is out of bounds.
     */
    default boolean argIsEquals(String[] args, int index, String value) {
        Optional<String> arg = getArg(args, index);
        return arg.isPresent() && arg.get().equalsIgnoreCase(value);
    }

    /**
     * @param args An array of arguments passed by the command sender
     * @param index The index of the argument to get.
     * @return The argument at the given index, or an empty optional if the index is out of bounds.
     */
    default Optional<String> getArg(String[] args, int index) {
        if(args.length > index) {
            return Optional.of(args[index]);
        }
        return Optional.empty();
    }

    /**
     * This method is called when the command is executed by a player.
     * @param player The player who executed the command.
     * @param args The arguments passed by the player.
     */
    public void onCommand(T player, String[] args);
}
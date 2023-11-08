package fr.nimelia.api.server;

import lombok.Getter;

public enum ServerType {

    PROXY("Proxy","PROXY-",2000),
    LOBBY("Lobby","LOBBY-",50),
    ;

    @Getter
    private final String name;
    @Getter
    private final String prefix;
    @Getter
    private final int slot;

    ServerType(String name, String prefix, int slot) {
        this.name = name;
        this.prefix = prefix;
        this.slot = slot;
    }

    public static ServerType fromValue(String value) {
        for (ServerType serverType : ServerType.values()) {
            if (value.startsWith(serverType.getPrefix())) {
                return serverType;
            }
        }
        throw new IllegalArgumentException("Aucun ServerType trouvé pour la valeur: " + value);
    }
}

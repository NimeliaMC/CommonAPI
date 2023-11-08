package fr.nimelia.api.server;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ServerInstance {


    private final String name;
    private final int port;
    private final ServerType type;
    @Setter
    private ServerStatus status;

    public ServerInstance(String name, int port, ServerType type, ServerStatus status) {
        this.name = name;
        this.port = port;
        this.type = type;
        this.status = status;
    }
}

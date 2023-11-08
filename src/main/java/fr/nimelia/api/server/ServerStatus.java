package fr.nimelia.api.server;

import lombok.Setter;

public enum ServerStatus {

    CREATING("en creation"),
    STARTING("démarrage"),
    PLAYING("en jeu"),
    END("fin"),
    ;
    private final String name;

    ServerStatus(String name) {
        this.name = name;
    }
}

package fr.nimelia.api.server.queues;

import fr.nimelia.api.server.ServerType;

import java.util.*;

public class Queue {

    private final Map<ServerType, List<UUID>> serversQueue;
    private final List<UUID> playersQueue;

    public Queue() {
        this.serversQueue = new HashMap<>();
        this.playersQueue = new ArrayList<>();
    }

    public boolean inQueue(UUID playerId) {
        return playersQueue.contains(playerId);
    }

    public void addPlayerToQueue(UUID playerId, ServerType serverType) {
        serversQueue
                .computeIfAbsent(serverType, k -> new ArrayList<>())
                .add(playerId);
    }

    public void removePlayerFromQueue(UUID playerId, ServerType serverType) {
        serversQueue
                .computeIfAbsent(serverType, k -> new ArrayList<>())
                .remove(playerId);
    }

    public List<UUID> getPlayersInQueue(ServerType serverType) {
        return new ArrayList<>(serversQueue.getOrDefault(serverType, new ArrayList<>()));
    }
}

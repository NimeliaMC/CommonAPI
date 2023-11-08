package fr.nimelia.api.moderation.punishment;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Punishment {

    private final UUID uniqueId;
    private final UUID owner;
    private final UUID target;
    private final String reason;
    private final long date;
    private final long time;

    public Punishment(UUID owner, UUID target, String reason, long time) {
        this.uniqueId = UUID.randomUUID();
        this.owner = owner;
        this.target = target;
        this.reason = reason;
        this.date = System.currentTimeMillis();
        this.time = time;
    }
}
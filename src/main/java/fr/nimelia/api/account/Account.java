package fr.nimelia.api.account;

import fr.nimelia.api.common.RankTypes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Account {

    private final UUID uuid;
    private final String name;
    private final String ip;
    private RankTypes rankTypes;
    private boolean receiveMp;
    private boolean receiveNotif;
    private int coins;
    private final List<UUID> friends;

    public Account(UUID uuid, String name, String ip, RankTypes rankTypes, boolean receiveMp, boolean receiveNotif, int coins, List<UUID> friends) {
        this.uuid = uuid;
        this.name = name;
        this.ip = ip;
        this.rankTypes = rankTypes;
        this.receiveMp = receiveMp;
        this.receiveNotif = receiveNotif;
        this.coins = coins;
        this.friends = friends;
    }
}

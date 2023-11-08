package fr.nimelia.api.common;

public enum RankTypes {

    ADMIN("§c",100,"administrateur","§c§lADMIN §f│ §c","§c§lADMIN §c"),
    RESPONSABLE("§6",90,"responsable","§6§lRESP §f│ §6","§6§lRESP §6"),
    DEVLOPPEUR("§e",80,"developpeur","§e§lDEV §f│ §e","§e§lDEV §e"),
    MODERATEUR("§3",70,"moderateur","§3§lMOD §f│ §3","§e§lMOD §3"),
    FRIEND("§d",50,"friend","§d§lAMI §f│ §d","§d§lAMI §d"),
    DEFAULT("§7", 1,"joueur","§7 ", "§7")
    ;

    private final String color;
    private final int weight;
    private final String name;
    private final String prefixTab;
    private final String prefixChat;


    RankTypes(String color, int weight, String name, String prefixTab, String prefixChat) {
        this.color = color;
        this.weight = weight;
        this.name = name;
        this.prefixTab = prefixTab;
        this.prefixChat = prefixChat;
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public String getPrefixTab() {
        return prefixTab;
    }

    public String getPrefixChat() {
        return prefixChat;
    }

    public final static String getRanks() {
        for (RankTypes rankTypes : RankTypes.values()) {
            return rankTypes.name();
        }
        return null;
    }
}
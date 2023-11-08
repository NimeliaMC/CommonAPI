package fr.nimelia.api.moderation.report;

public enum ReportType {

    CHAT("Chat", "§cReport Chat"),
    INGAME("Ingame", "§cReport Ingame"),
    ;

    private final String name;
    private final String inventoryName;

    ReportType(String name, String inventoryName){
        this.inventoryName = inventoryName;
        this.name = name;
    }
}

package fr.nimelia.api.moderation.punishment;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import fr.nimelia.api.CommonAPI;
import org.bson.Document;

public class PunishmentManager {

    private final CommonAPI api;
    private final Map<UUID, Punishment> punishments = new HashMap<>();

    public PunishmentManager(CommonAPI api) {
        this.api = api;
        this.loadPunishments();
    }

    private void loadPunishments() {
        for (Document document : getCollection().find()) {
            Gson gson = CommonAPI.GSON;
            Punishment punishment = gson.fromJson(document.toJson(), Punishment.class);
            punishments.put(punishment.getUniqueId(), punishment);
        }
    }

    public void createPunishment(UUID owner, UUID target, String reason, long time) {
        if (isPunishmentExists(owner, target)) return;

        Punishment punishment = new Punishment(owner, target, reason, time);
        Gson gson = CommonAPI.GSON;
        String json = gson.toJson(punishment);
        Document document = Document.parse(json);
        getCollection().insertOne(document);
        punishments.put(punishment.getUniqueId(), punishment);
    }

    public void updatePunishment(Punishment punishment) {
        if (!isPunishmentExists(punishment.getOwner(), punishment.getTarget())) return;

        Gson gson = CommonAPI.GSON;
        String json = gson.toJson(punishment);
        Document document = Document.parse(json);
        getCollection().updateOne(Filters.eq("uuid", punishment.getUniqueId().toString()), Updates.set("json", document));
        punishments.put(punishment.getUniqueId(), punishment);
    }

    public Punishment getPunishment(UUID owner, UUID target) {
        if (!isPunishmentExists(owner, target)) return null;
        for (Punishment punishment : punishments.values()) {
            if (punishment != null && punishment.getOwner().equals(owner) && punishment.getTarget().equals(target)) {
                return punishment;
            }
        }
        return null;
    }

    public List<Punishment> getPunishmentsByOwner(UUID owner) {
        List<Punishment> list = new ArrayList<>();
        for (Punishment punishment : punishments.values()) {
            if (punishment != null && punishment.getOwner().equals(owner)) {
                list.add(punishment);
            }
        }
        return list;
    }

    public List<Punishment> getPunishmentsByTarget(UUID target) {
        List<Punishment> list = new ArrayList<>();
        for (Punishment punishment : punishments.values()) {
            if (punishment != null && punishment.getTarget().equals(target)) {
                list.add(punishment);
            }
        }
        return list;
    }

    public boolean isPunishmentExists(UUID owner, UUID target) {
        for (Punishment punishment : punishments.values()) {
            if (punishment != null && punishment.getOwner().equals(owner) && punishment.getTarget().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public MongoCollection<Document> getCollection() {
        return api.getMongo().getCollection("punishments");
    }
}


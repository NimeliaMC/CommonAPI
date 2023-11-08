package fr.nimelia.api.account;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import fr.nimelia.api.CommonAPI;
import fr.nimelia.api.common.RankTypes;
import lombok.Getter;
import org.bson.Document;

import java.util.*;

@Getter
public class AccountManager {

    private final CommonAPI api;
    private final Map<UUID, Account> accounts = new HashMap<>();

    public AccountManager(CommonAPI api) {
        this.api = api;
        this.loadAccounts();
    }

    private void loadAccounts() {
        for (Document document : getCollection().find()) {
            Gson gson = CommonAPI.GSON;
            Account account = gson.fromJson(document.toJson(), Account.class);
            accounts.put(account.getUuid(), account);
        }
    }

    public void createAccount(UUID uuid, String name, String ip, RankTypes rankTypes, boolean receiveMp, boolean receiveNotif, int coins, List<UUID> friends) {
        if (isAccountExists(uuid)) return;

        Account account = new Account(uuid, name, ip, rankTypes, receiveMp, receiveNotif, coins, friends);
        Gson gson = CommonAPI.GSON;
        String json = gson.toJson(account);
        Document document = Document.parse(json);
        getCollection().insertOne(document);
        accounts.put(uuid, account);
    }

    public void updateAccount(Account account) {
        if (!isAccountExists(account.getUuid())) return;

        Gson gson = CommonAPI.GSON;
        String json = gson.toJson(account);
        Document document = Document.parse(json);
        getCollection().updateOne(Filters.eq("uuid", account.getUuid().toString()), Updates.set("json", document));
        accounts.put(account.getUuid(), account);
    }

    public Account getAccount(UUID uuid) {
        return accounts.get(uuid);
    }

    public List<Account> getAccountsByRank(RankTypes rankTypes) {
        List<Account> list = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getRankTypes() == rankTypes) {
                list.add(account);
            }
        }
        return list;
    }

    public List<Account> getAccountsByName(String name) {
        List<Account> list = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getName().equalsIgnoreCase(name)) {
                list.add(account);
            }
        }
        return list;
    }

    public List<Account> getFriends(UUID uuid) {
        Account account = getAccount(uuid);
        if (account != null) {
            List<UUID> friendUuids = account.getFriends();
            List<Account> friends = new ArrayList<>();
            for (UUID friendUuid : friendUuids) {
                Account friend = getAccount(friendUuid);
                if (friend != null) {
                    friends.add(friend);
                }
            }
            return friends;
        }
        return Collections.emptyList();
    }

    public boolean isAccountExists(UUID uuid) {
        return accounts.containsKey(uuid);
    }

    public MongoCollection<Document> getCollection() {
        return api.getMongo().getCollection("accounts");
    }
}
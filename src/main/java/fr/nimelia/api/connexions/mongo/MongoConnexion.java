package fr.nimelia.api.connexions.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

@Getter
public class MongoConnexion {

    private final MongoClient client;
    private final MongoDatabase database;

    public MongoConnexion() {
        this.client = MongoClients.create();
        this.database = client.getDatabase("minecraft");
    }

    public MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }

}
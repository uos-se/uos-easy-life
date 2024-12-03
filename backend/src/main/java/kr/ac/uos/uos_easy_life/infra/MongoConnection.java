package kr.ac.uos.uos_easy_life.infra;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class MongoConnection {
  private final MongoDatabase database;

  public MongoConnection() {
    String connectionString = "mongodb://localhost:27017/uos-easy-life";
    String databaseName = "uos-easy-life";

    MongoClient mongoClient = MongoClients.create(connectionString);
    database = mongoClient.getDatabase(databaseName);
  }

  public MongoCollection<Document> getCollection(String collectionName) {
    return database.getCollection(collectionName);
  }
}

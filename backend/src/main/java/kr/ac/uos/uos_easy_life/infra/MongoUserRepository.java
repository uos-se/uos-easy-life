package kr.ac.uos.uos_easy_life.infra;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

import kr.ac.uos.uos_easy_life.core.interfaces.UserRepository;
import kr.ac.uos.uos_easy_life.core.model.User;

@Repository
public class MongoUserRepository implements UserRepository {

  private final MongoCollection<Document> userCollection;

  public MongoUserRepository(MongoConnection connection) {
    this.userCollection = connection.getCollection("user");
  }

  @Override
  public User findById(String id) {
    Document document = userCollection.find(new Document("_id", id)).first();
    return document != null ? documentToUser(document) : null;
  }

  @Override
  public User findByPortalId(String portalId) {
    Document document = userCollection.find(new Document("portalId", portalId)).first();
    return document != null ? documentToUser(document) : null;
  }

  @Override
  public void save(User user) {
    User existingUser = this.findById(user.getId());
    if (existingUser != null) {
      throw new RuntimeException("User already exists");
    }

    Document document = userToDocument(user);
    userCollection.insertOne(document);
  }

  @Override
  public void update(User user) {
    Document updatedDocument = new Document("$set", userToDocument(user));
    userCollection.updateOne(new Document("_id", user.getId()), updatedDocument);
  }

  @Override
  public void deleteById(String id) {
    userCollection.deleteOne(new Document("_id", id));
  }

  private User documentToUser(Document document) {
    User user = new User(
        document.getString("_id"),
        document.getString("name"),
        document.getString("student_id"),
        document.getInteger("current_grade"),
        document.getInteger("current_semester"),
        document.getString("portal_id"),
        document.getString("hashed_portal_password"),
        document.getString("salt"));
    return user;
  }

  private Document userToDocument(User user) {
    Document document = new Document("_id", user.getId())
        .append("portalId", user.getPortalId())
        .append("name", user.getName())
        .append("student_id", user.getStudentId())
        .append("current_grade", user.getCurrentGrade())
        .append("current_semester", user.getCurrentSemester())
        .append("portal_id", user.getPortalId())
        .append("hashed_portal_password", user.getHashedPortalPassword())
        .append("salt", user.getSalt());

    return document;
  }
}

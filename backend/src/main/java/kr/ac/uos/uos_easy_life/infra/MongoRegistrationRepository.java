package kr.ac.uos.uos_easy_life.infra;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

import kr.ac.uos.uos_easy_life.core.interfaces.RegistrationRepository;

@Repository
public class MongoRegistrationRepository implements RegistrationRepository {

  // registration list per user
  private final MongoCollection<Document> registrationCollection;

  public MongoRegistrationRepository(MongoConnection mongoConnection) {
    this.registrationCollection = mongoConnection.getCollection("registration");
  }

  @Override
  public void register(String userId, String courseId) {
    Document oldRegistration = registrationCollection.find(new Document("_id", userId)).first();

    // If registration data is not in DB
    if (oldRegistration == null) {
      oldRegistration = new Document("_id", userId)
          .append("registered_courses", List.of(courseId));
      registrationCollection.insertOne(oldRegistration);
      return;
    }
    // update process
    List<String> updatedList = (List<String>) oldRegistration.get("registered_courses");
    if (updatedList.contains(courseId)) {
      return;
    }

    updatedList.add(courseId);
    registrationCollection.updateOne(new Document("_id", userId),
        new Document("$set", new Document("registered_courses", updatedList)));
  }

  @Override
  public void unregister(String userId, String courseId) {
    Document oldRegistration = registrationCollection.find(new Document("_id", userId)).first();

    // If registration data is not in DB
    if (oldRegistration == null) {
      return;
    }
    // update process
    List<String> updatedList = (List<String>) oldRegistration.get("registered_courses");
    if (!updatedList.contains(courseId)) {
      return;
    }

    updatedList.remove(courseId);
    registrationCollection.updateOne(new Document("_id", userId),
        new Document("$set", new Document("registered_courses", updatedList)));
  }

  @Override
  public List<String> findRegisteredCourses(String userId) {
    Document document = registrationCollection.find(new Document("_id", userId)).first();
    if (document == null) {
      document = new Document("_id", userId)
          .append("registered_courses", new ArrayList<String>());
      registrationCollection.insertOne(document);
    }

    List<String> registeredCourses = (List<String>) document.get("registered_courses");
    return registeredCourses;
  }
}
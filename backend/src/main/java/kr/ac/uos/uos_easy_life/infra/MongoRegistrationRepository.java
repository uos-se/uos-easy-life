package kr.ac.uos.uos_easy_life.infra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    UserRegistrationDTO userRegistration = findUserRegistration(userId);
    if (userRegistration == null) {
      registrationCollection.insertOne(
          new Document("_id", userId)
              .append("registered_courses", List.of(courseId)));

      return;
    }

    if (userRegistration.getRegisteredCourses().contains(courseId)) {
      return;
    }

    List<String> updatedRegistration = userRegistration.getRegisteredCourses();
    updatedRegistration.add(userId);

    registrationCollection.updateOne(new Document("_id", userId),
        new Document("$set", new Document("registered_courses", updatedRegistration)));
  }

  @Override
  public void unregister(String userId, String courseId) {
    UserRegistrationDTO userRegistration = findUserRegistration(userId);
    if (userRegistration == null) {
      return;
    }

    List<String> registeredCourses = userRegistration.getRegisteredCourses();
    if (!registeredCourses.contains(courseId)) {
      return;
    }

    registeredCourses.remove(courseId);
    registrationCollection.updateOne(new Document("_id", userId),
        new Document("$set", new Document("registeredCourses", registeredCourses)));
  }

  @Override
  public List<String> findRegisteredCourses(String userId) {
    UserRegistrationDTO userRegistration = findUserRegistration(userId);
    if (userRegistration == null) {
      return null;
    }

    return userRegistration.getRegisteredCourses();
  }

  private UserRegistrationDTO findUserRegistration(String userId) {
    Document document = registrationCollection.find(new Document("_id", userId)).first();
    return document != null ? documentToUserRegistration(document) : null;
  }

  private UserRegistrationDTO documentToUserRegistration(Document document) {
    String userId = document.getString("_id");
    Object obj = document.get("registered_courses");

    List<String> registeredCourses = new ArrayList<>();
    if (obj instanceof List) {
      for (Object course : (List<?>) obj) {
        if (course instanceof String) {
          registeredCourses.add((String) course);
        }
      }
    }

    return new UserRegistrationDTO(userId, registeredCourses);
  }
}

class UserRegistrationDTO {
  private String userId;
  private List<String> registeredCourses;

  public UserRegistrationDTO(String userId, List<String> registeredCourses) {
    this.userId = userId;
    this.registeredCourses = registeredCourses;
  }

  public String getUserId() {
    return userId;
  }

  public List<String> getRegisteredCourses() {
    return registeredCourses;
  }
}
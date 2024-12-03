package kr.ac.uos.uos_easy_life.infra;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

import kr.ac.uos.uos_easy_life.core.interfaces.AcademicStatusRepository;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;

@Repository
public class MongoAcademicStatusRepository implements AcademicStatusRepository {

  private final MongoCollection<Document> academicStatusCollection;

  public MongoAcademicStatusRepository(MongoConnection connection) {
    this.academicStatusCollection = connection.getCollection("academic_status");
  }

  @Override
  public void setAcademicStatus(String userId, UserAcademicStatus academicStatus) {
    UserAcademicStatus previousAcademicStatus = getAcademicStatus(userId);
    Document updatedAcademicStatus = userAcademicStatusToDocument(userId, academicStatus);

    if (previousAcademicStatus == null) {
      academicStatusCollection.insertOne(updatedAcademicStatus);
      return;
    }

    academicStatusCollection.updateOne(new Document("_id", userId), new Document("$set", updatedAcademicStatus));
  }

  @Override
  public UserAcademicStatus getAcademicStatus(String userId) {
    Document document = academicStatusCollection.find(new Document("_id", userId)).first();
    return document != null ? documentToUserAcademicStatus(document) : null;
  }

  private UserAcademicStatus documentToUserAcademicStatus(Document document) {
    return new UserAcademicStatus(
        document.getInteger("totalCompletedCredit"),
        document.getInteger("majorCompletedCredit"),
        document.getInteger("majorEssentialCompletedCredit"),
        document.getInteger("liberalCompletedCredit"),
        document.getInteger("liberalEssentialCompletedCredit"),
        document.getInteger("engineeringCompletedCredit"),
        document.getInteger("generalCompletedCredit"),
        document.getDouble("totalGradePointAverage"));
  }

  private Document userAcademicStatusToDocument(String userId, UserAcademicStatus userAcademicStatus) {
    return new Document("_id", userId)
        .append("totalCompletedCredit", userAcademicStatus.getTotalCompletedCredit())
        .append("majorCompletedCredit", userAcademicStatus.getMajorCompletedCredit())
        .append("majorEssentialCompletedCredit", userAcademicStatus.getMajorEssentialCompletedCredit())
        .append("liberalCompletedCredit", userAcademicStatus.getLiberalCompletedCredit())
        .append("liberalEssentialCompletedCredit", userAcademicStatus.getLiberalEssentialCompletedCredit())
        .append("engineeringCompletedCredit", userAcademicStatus.getEngineeringCompletedCredit())
        .append("generalCompletedCredit", userAcademicStatus.getGeneralCompletedCredit())
        .append("totalGradePointAverage", userAcademicStatus.getTotalGradePointAverage());
  }

}
package kr.ac.uos.uos_easy_life.infra;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

import kr.ac.uos.uos_easy_life.core.interfaces.CoursePlanRepository;
import kr.ac.uos.uos_easy_life.core.model.CoursePlan;

class CoursePlanDocument {
    private final String userId;
    private final List<CoursePlan> coursePlans;

    public CoursePlanDocument(String userId, List<CoursePlan> coursePlans) {
        this.userId = userId;
        this.coursePlans = coursePlans;
    }

    public String getUserId() {
        return userId;
    }

    public List<CoursePlan> getCoursePlans() {
        return coursePlans;
    }

    public Document toDocument() {
        return new Document("_id", userId)
                .append("coursePlans", coursePlans);
    }

    public static CoursePlanDocument fromDocument(Document document) {
        return new CoursePlanDocument(
                document.getString("_id"),
                document.getList("coursePlans", CoursePlan.class));
    }
}

@Repository
public class MongoCoursePlanRepository implements CoursePlanRepository {
    private final MongoCollection<Document> coursePlanCollection;

    public MongoCoursePlanRepository(MongoConnection connection) {
        this.coursePlanCollection = connection.getCollection("course_plan");
    }

    @Override
    public void setCoursePlan(String userId, List<CoursePlan> coursePlans) {
        Document document = new CoursePlanDocument(userId, coursePlans).toDocument();
        coursePlanCollection.updateOne(new Document("_id", userId), new Document("$set", document));
    }

    @Override
    public List<CoursePlan> getCoursePlan(String userId) {
        Document document = coursePlanCollection.find(new Document("_id", userId)).first();
        return document != null ? CoursePlanDocument.fromDocument(document).getCoursePlans() : null;
    }
}

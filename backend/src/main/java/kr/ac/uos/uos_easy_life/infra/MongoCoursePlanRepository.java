package kr.ac.uos.uos_easy_life.infra;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;

import kr.ac.uos.uos_easy_life.core.interfaces.CoursePlanRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.CoursePlan;
import kr.ac.uos.uos_easy_life.core.model.Department;

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

  private Department parseDepartment(Document document) {
    int departmentCode = document.getInteger("departmentCode");
    return Department.fromDepartmentCode(departmentCode);
  }

  private Course parseCourse(Document document) {
    return new Course(
        document.getString("id"),
        document.getString("lectureName"),
        document.getString("lectureCode"),
        document.getInteger("lectureCredit"),
        document.getInteger("lectureGrade"),
        parseDepartment(document.get("department", Document.class)),
        document.getBoolean("isMajorElective"),
        document.getBoolean("isMajorEssential"),
        document.getBoolean("isLiberalElective"),
        document.getBoolean("isLiberalEssential"),
        document.getBoolean("isEngineering"),
        document.getBoolean("isBasicAcademic"),
        document.getInteger("designCredit"),
        document.getInteger("lastOpenYear"));
  }

  private CoursePlan parseCoursePlan(Document document) {
    String id = document.getString("id");
    int semesterNumber = document.getInteger("semesterNumber");
    List<Course> courses = new ArrayList<>();
    for (Document courseDocument : document.getList("courses", Document.class)) {
      courses.add(parseCourse(courseDocument));
    }
    return new CoursePlan(id, semesterNumber, courses);
  }

  private Document dumpDepartment(Department department) {
    return new Document("departmentCode", department.getDepartmentCode());
  }

  private Document dumpCourse(Course course) {
    return new Document("id", course.getId())
        .append("lectureName", course.getLectureName())
        .append("lectureCode", course.getLectureCode())
        .append("lectureCredit", course.getLectureCredit())
        .append("lectureGrade", course.getLectureGrade())
        .append("department", dumpDepartment(course.getDepartment()))
        .append("isMajorElective", course.isMajorElective())
        .append("isMajorEssential", course.isMajorEssential())
        .append("isLiberalElective", course.isLiberalElective())
        .append("isLiberalEssential", course.isLiberalEssential())
        .append("isEngineering", course.isEngineering())
        .append("isBasicAcademic", course.isBasicAcademic())
        .append("designCredit", course.getDesignCredit())
        .append("lastOpenYear", course.getLastOpenYear());
  }

  private Document dumpCoursePlan(CoursePlan coursePlan) {
    List<Document> courseDocuments = new ArrayList<>();
    for (Course course : coursePlan.getCourses()) {
      courseDocuments.add(dumpCourse(course));
    }
    return new Document("id", coursePlan.getId())
        .append("semesterNumber", coursePlan.getSemesterNumber())
        .append("courses", courseDocuments);
  }

  public Document toDocument() {
    List<Document> coursePlanDocuments = new ArrayList<>();
    for (CoursePlan coursePlan : coursePlans) {
      coursePlanDocuments.add(dumpCoursePlan(coursePlan));
    }
    return new Document("_id", userId)
        .append("coursePlans", coursePlanDocuments);
  }

  public static CoursePlanDocument fromDocument(Document document) {
    String userId = document.getString("_id");
    List<CoursePlan> coursePlans = new ArrayList<>();
    for (Document coursePlanDocument : document.getList("coursePlans", Document.class)) {
      coursePlans.add(new CoursePlanDocument(userId, coursePlans).parseCoursePlan(coursePlanDocument));
    }
    return new CoursePlanDocument(userId, coursePlans);
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
    coursePlanCollection.replaceOne(new Document("_id", userId), document, new ReplaceOptions().upsert(true));
  }

  @Override
  public List<CoursePlan> getCoursePlan(String userId) {
    Document document = coursePlanCollection.find(new Document("_id", userId)).first();
    return document != null ? CoursePlanDocument.fromDocument(document).getCoursePlans() : null;
  }
}

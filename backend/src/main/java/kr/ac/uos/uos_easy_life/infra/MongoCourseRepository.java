package kr.ac.uos.uos_easy_life.infra;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

import kr.ac.uos.uos_easy_life.core.interfaces.CourseRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.Department;

@Repository
public class MongoCourseRepository implements CourseRepository {

  private final MongoCollection<Document> courseCollection;

  public MongoCourseRepository(MongoConnection connection) {
    this.courseCollection = connection.getCollection("course");
  }

  @Override
  public Course findById(String id) {
    Document document = courseCollection.find(new Document("_id", id)).first();
    return document != null ? documentToCourse(document) : null;
  }

  @Override
  public Course findByCode(String code) {
    Document document = courseCollection.find(new Document("lecture_code", code)).first();
    return document != null ? documentToCourse(document) : null;
  }

  @Override
  public Course findByName(String name) {
    Document document = courseCollection.find(new Document("lecture_name", name)).first();
    return document != null ? documentToCourse(document) : null;
  }

  @Override
  public List<Course> findAll() {
    List<Document> documents = courseCollection.find().into(new ArrayList<>());
    List<Course> courses = new ArrayList<>();

    for (Document document : documents) {
      courses.add(documentToCourse(document));
    }

    return courses;
  }

  @Override
  public void save(Course course) {
    Document document = courseToDocument(course);
    courseCollection.insertOne(document);
  }

  @Override
  public void update(Course course) {
    Document updatedDocument = new Document("$set", courseToDocument(course));
    courseCollection.updateOne(new Document("_id", course.getId()), updatedDocument);
  }

  @Override
  public void deleteById(String id) {
    courseCollection.deleteOne(new Document("_id", id));
  }

  private Course documentToCourse(Document document) {
    Course course = new Course(
        document.getString("_id"),
        document.getString("lecture_name"),
        document.getString("lecture_code"),
        document.getInteger("lecture_credit"),
        document.getInteger("lecture_grade"),
        (Department) document.get("department"),
        document.getBoolean("is_major_elective", false),
        document.getBoolean("is_major_essential", false),
        document.getBoolean("is_liberal_elective", false),
        document.getBoolean("is_liberal_essential", false),
        document.getBoolean("is_engineering", false),
        document.getBoolean("is_basic_academic", false),
        document.getInteger("design_credit", 0));

    return course;
  }

  private Document courseToDocument(Course course) {
    return new Document("_id", course.getId())
        .append("lecture_name", course.getLectureName())
        .append("lecture_code", course.getLectureCode())
        .append("lecture_credit", course.getLectureCredit())
        .append("lecture_grade", course.getLectureGrade())
        .append("department", course.getDepartment())
        .append("is_major_elective", course.isMajorElective())
        .append("is_major_essential", course.isMajorEssential())
        .append("is_liberal_elective", course.isLiberalElective())
        .append("is_liberal_essential", course.isLiberalEssential())
        .append("is_engineering", course.isEngineering())
        .append("is_basic_academic", course.isBasicAcademic())
        .append("design_credit", course.getDesignCredit());
  }
}

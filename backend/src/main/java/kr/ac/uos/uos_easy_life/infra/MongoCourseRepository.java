package kr.ac.uos.uos_easy_life.infra;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;

import kr.ac.uos.uos_easy_life.core.interfaces.CourseRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.Department;

@Repository
public class MongoCourseRepository implements CourseRepository {

  private final MongoCollection<Document> courseCollection;

  private static int parseGrade(String grade) {
    // If string is int-parsable, return int value
    try {
      return Integer.parseInt(grade);
    } catch (NumberFormatException e) {
    }

    // Split it by '/' and get the first element
    String[] grades = grade.split("/");
    try {
      return Integer.parseInt(grades[0]);
    } catch (NumberFormatException e) {
    }

    // If it's not parsable, return 1
    return 1;
  }

  public MongoCourseRepository(MongoConnection connection) throws IOException {
    this.courseCollection = connection.getCollection("course");

    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock-course-data.json");

    // JSON 파일을 읽어와 Course 객체로 변환 후 저장
    JSONObject json = new JSONObject(new String(inputStream.readAllBytes(), "UTF-8"));
    JSONArray courseList = json.getJSONArray("dsMain");

    // Option for insert course info or ignore if already exist
    ReplaceOptions upsertOption = new ReplaceOptions().upsert(true);

    for (int i = 0; i < courseList.length(); i++) {
      JSONObject courseJson = courseList.getJSONObject(i);

      String id = "COURSE_" + (i + 1); // 몰?루
      String lectureName = courseJson.getString("SBJC_NM");
      String lectureCode = courseJson.getString("SBJC_NO");
      int lectureCredit = courseJson.getInt("CMPN_PNT");
      int lectureGrade = parseGrade(courseJson.getString("CMPN_GRADE"));
      Department department = Department.fromDepartmentName(courseJson.getString("OGDP_SCSBJT_NM"));
      boolean isMajorElective = courseJson.getString("COURSE_DIVCD_NM").equals("전공선택");
      boolean isMajorEssential = courseJson.getString("COURSE_DIVCD_NM").equals("전공필수");
      boolean isLiberalElective = courseJson.getString("COURSE_DIVCD_NM").equals("교양선택");
      boolean isLiberalEssential = courseJson.getString("COURSE_DIVCD_NM").equals("교양필수");
      boolean isEngineering = courseJson.getString("COURSE_DIVCD_NM2").equals("공학소양");
      boolean isBasicAcademic = courseJson.getString("COURSE_DIVCD_NM2").equals("학문기초");
      int designCredit = courseJson.getInt("DESIGN_PNT");

      Course course = new Course(
          id,
          lectureName,
          lectureCode,
          lectureCredit,
          lectureGrade,
          department,
          isMajorElective,
          isMajorEssential,
          isLiberalElective,
          isLiberalEssential,
          isEngineering,
          isBasicAcademic,
          designCredit);

      Document filter = new Document("_id", id);
      Document document = courseToDocument(course);
      this.courseCollection.replaceOne(filter, document, upsertOption);
    }
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
        document.get("department") == null ? Department.fromDepartmentCode(0)
            : Department.fromDepartmentName(document.getString("department")),
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
        .append("department", course.getDepartment() == null ? null : course.getDepartment().getDepartmentName())
        .append("is_major_elective", course.isMajorElective())
        .append("is_major_essential", course.isMajorEssential())
        .append("is_liberal_elective", course.isLiberalElective())
        .append("is_liberal_essential", course.isLiberalEssential())
        .append("is_engineering", course.isEngineering())
        .append("is_basic_academic", course.isBasicAcademic())
        .append("design_credit", course.getDesignCredit());
  }
}

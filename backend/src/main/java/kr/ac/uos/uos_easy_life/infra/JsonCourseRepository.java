package kr.ac.uos.uos_easy_life.infra;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import kr.ac.uos.uos_easy_life.core.interfaces.CourseRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.Department;

@Repository
public class JsonCourseRepository implements CourseRepository {

  // Course 리스트를 이용해 데이터를 저장
  private List<Course> courses = new ArrayList<>();

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

  public JsonCourseRepository() throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("course-data.json");
    // JSON 파일을 읽어와 Course 객체로 변환 후 저장
    JSONArray courseList = new JSONArray(new String(inputStream.readAllBytes(), "UTF-8"));
    for (int i = 0; i < courseList.length(); i++) {
      JSONObject courseJson = courseList.getJSONObject(i);

      String id = courseJson.getString("_id");
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

      courses.add(course);
    }
  }

  @Override
  public Course findById(String id) {
    // 주어진 ID에 해당하는 강의를 찾아 반환
    Optional<Course> course = courses.stream().filter(c -> c.getId().equals(id)).findFirst();
    return course.orElse(null); // 없으면 null 반환
  }

  @Override
  public Course findByCode(String code) {
    // 주어진 코드에 해당하는 강의를 찾아 반환
    Optional<Course> course = courses.stream().filter(c -> c.getLectureCode().equals(code)).findFirst();
    return course.orElse(null); // 없으면 null 반환
  }

  @Override
  public Course findByName(String name) {
    // 주어진 이름에 해당하는 강의를 찾아 반환
    Optional<Course> course = courses.stream().filter(c -> c.getLectureName().equals(name)).findFirst();
    return course.orElse(null); // 없으면 null 반환
  }

  @Override
  public List<Course> findByDepartment(Department department) {
    // 주어진 학과에 해당하는 강의를 찾아 반환
    List<Course> departmentCourses = new ArrayList<>();
    for (Course course : courses) {
      if (course.getDepartment() == department) {
        departmentCourses.add(course);
      }
    }
    return departmentCourses;
  }

  @Override
  public List<Course> findAll() {
    // 저장된 모든 강의를 반환
    return new ArrayList<>(courses);
  }

  @Override
  public void save(Course course) {
    // 새로운 강의를 리스트에 추가
    courses.add(course);
  }

  @Override
  public void update(Course course) {
    // 주어진 코스의 ID에 해당하는 강의를 찾아 업데이트
    for (int i = 0; i < courses.size(); i++) {
      if (courses.get(i).getId().equals(course.getId())) {
        courses.set(i, course);
        return;
      }
    }
  }

  @Override
  public void deleteById(String id) {
    // 주어진 ID에 해당하는 강의를 리스트에서 삭제
    courses.removeIf(course -> course.getId().equals(id));
  }
}

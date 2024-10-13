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

@Repository
public class MockCourseRepository implements CourseRepository {

  // Course 리스트를 이용해 데이터를 저장
  private List<Course> courses = new ArrayList<>();

  public MockCourseRepository() throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock-course-data.json");
    // JSON 파일을 읽어와 Course 객체로 변환 후 저장
    JSONObject json = new JSONObject(new String(inputStream.readAllBytes(), "UTF-8"));
    JSONArray courseList = json.getJSONArray("dsMain");
    for (int i = 0; i < courseList.length(); i++) {
      JSONObject courseJson = courseList.getJSONObject(i);

      String id = "COURSE_" + (i + 1);
      String lectureName = courseJson.getString("SBJC_NM");
      String lectureCode = courseJson.getString("SBJC_NO");
      int lectureCredit = courseJson.getInt("CMPN_PNT");
      int lectureGrade = 1; // FIXME: 학년 정보를 3/4따위로 문자열로 박아놓은 경우가 있어서 임시로 1로 설정

      Course course = new Course(id, lectureName, lectureCode, lectureCredit,
          lectureGrade);

      courses.add(course);
    }
  }

  @Override
  public Course findById(String id) {
    // 주어진 ID에 해당하는 코스를 찾아 반환
    Optional<Course> course = courses.stream().filter(c -> c.getId().equals(id)).findFirst();
    return course.orElse(null); // 없으면 null 반환
  }

  @Override
  public Course findByCode(String code) {
    // 주어진 코드에 해당하는 코스를 찾아 반환
    Optional<Course> course = courses.stream().filter(c -> c.getLectureCode().equals(code)).findFirst();
    return course.orElse(null); // 없으면 null 반환
  }

  @Override
  public List<Course> findAll() {
    // 저장된 모든 코스를 반환
    return new ArrayList<>(courses);
  }

  @Override
  public void save(Course course) {
    // 새로운 코스를 리스트에 추가
    courses.add(course);
  }

  @Override
  public void update(Course course) {
    // 주어진 코스의 ID에 해당하는 코스를 찾아 업데이트
    for (int i = 0; i < courses.size(); i++) {
      if (courses.get(i).getId().equals(course.getId())) {
        courses.set(i, course);
        return;
      }
    }
  }

  @Override
  public void deleteById(String id) {
    // 주어진 ID에 해당하는 코스를 리스트에서 삭제
    courses.removeIf(course -> course.getId().equals(id));
  }
}

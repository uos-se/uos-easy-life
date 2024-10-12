package kr.ac.uos.uos_easy_life.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import kr.ac.uos.uos_easy_life.core.interfaces.CourseRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;

@Repository
public class MockCourseRepository implements CourseRepository {

  // Course 리스트를 이용해 데이터를 저장
  private List<Course> courses = new ArrayList<>();

  @Override
  public Course findById(String id) {
    // 주어진 ID에 해당하는 코스를 찾아 반환
    Optional<Course> course = courses.stream().filter(c -> c.getId().equals(id)).findFirst();
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

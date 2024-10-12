package kr.ac.uos.uos_easy_life.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import kr.ac.uos.uos_easy_life.core.interfaces.RegistrationRepository;

@Repository
public class MockRegistrationRepository implements RegistrationRepository {

  // 사용자별로 등록된 강의를 저장할 리스트
  private Map<String, List<String>> registrationData = new HashMap<>();

  @Override
  public void register(String userId, String courseId) {
    // 사용자가 없으면 새로운 리스트를 생성
    registrationData.putIfAbsent(userId, new ArrayList<>());

    // 중복된 강의가 등록되지 않도록 체크 후 추가
    List<String> registeredCourses = registrationData.get(userId);
    if (!registeredCourses.contains(courseId)) {
      registeredCourses.add(courseId);
    }
  }

  @Override
  public void unregister(String userId, String courseId) {
    // 사용자가 등록된 강의가 있다면 해당 강의를 제거
    List<String> registeredCourses = registrationData.get(userId);
    if (registeredCourses != null) {
      registeredCourses.remove(courseId);
    }
  }

  @Override
  public List<String> findRegisteredCourses(String userId) {
    // 사용자의 등록된 강의 ID 목록을 가져오고, 그 목록을 Course 객체로 변환해서 반환
    List<String> registeredCourseIds = registrationData.getOrDefault(userId, new ArrayList<>());
    return registeredCourseIds;
  }
}

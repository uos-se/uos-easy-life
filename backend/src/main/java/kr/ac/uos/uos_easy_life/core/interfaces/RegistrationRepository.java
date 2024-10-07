package kr.ac.uos.uos_easy_life.core.interfaces;

import java.util.List;

public interface RegistrationRepository {
  public void register(String userId, String courseId);

  public void unregister(String userId, String courseId);

  public List<String> findRegisteredCourses(String userId);
}

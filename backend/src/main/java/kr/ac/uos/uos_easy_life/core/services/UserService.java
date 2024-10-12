package kr.ac.uos.uos_easy_life.core.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import kr.ac.uos.uos_easy_life.core.interfaces.CourseRepository;
import kr.ac.uos.uos_easy_life.core.interfaces.RegistrationRepository;
import kr.ac.uos.uos_easy_life.core.interfaces.UosApi;
import kr.ac.uos.uos_easy_life.core.interfaces.UosSessionManager;
import kr.ac.uos.uos_easy_life.core.interfaces.UserRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.User;
import kr.ac.uos.uos_easy_life.core.model.UserFullInfo;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final RegistrationRepository registrationRepository;
  private final UosSessionManager uosSessionManager;
  private final UosApi uosApi;

  public UserService(UserRepository userRepository, CourseRepository courseRepository,
      RegistrationRepository registrationRepository, UosSessionManager uosSessionManager,
      UosApi uosApi) {
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.registrationRepository = registrationRepository;
    this.uosSessionManager = uosSessionManager;
    this.uosApi = uosApi;
  }

  public User getUser(String userId) {
    return userRepository.findById(userId);
  }

  public UserFullInfo getUserFullInfo(String userId) {
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
    }

    List<String> courseIds = registrationRepository.findRegisteredCourses(userId);
    List<Course> courses = new ArrayList<>();
    for (String courseId : courseIds) {
      Course course = courseRepository.findById(courseId);
      if (course == null) {
        throw new IllegalArgumentException("강의가 존재하지 않습니다.");
      }
      courses.add(course);
    }

    return new UserFullInfo(user.getName(), user.getStudentId(), "", "",
        user.getDepartments().getFirst().getDepartmentName(), user.getCurrentGrade(), "", courses);
  }

  public void syncUser(String userId, String portalId, String portalPassword) {
    // Validate user
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
    }
    if (user.getPortalId() != portalId) {
      throw new IllegalArgumentException("포탈 아이디가 일치하지 않습니다.");
    }
    user.checkPassword(portalPassword);

    // Get session
    UosSession session = uosSessionManager.createUosSession(portalId, portalPassword);

    // Retrieve user course information
    List<Course> courses = uosApi.getUserCourseList(session);
    for (Course course : courses) {
      registrationRepository.register(userId, course.getId());
    }
  }
}

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
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatusDTO;
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
        user.getDepartment().getDepartmentName(), user.getCurrentGrade(), "", courses);
  }

  public void syncUser(String userId, String portalId, String portalPassword) {
    // Validate user
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
    }
    if (!user.getPortalId().equals(portalId)) {
      throw new IllegalArgumentException("포탈 아이디가 일치하지 않습니다.");
    }
    user.checkPassword(portalPassword);

    // Get session
    UosSession session = uosSessionManager.createUosSession(portalId, portalPassword);

    // Retrieve user course information
    List<String> courseCodes = uosApi.getUserCourseCodes(session, user.getStudentId());

    // Register courses
    for (String courseCode : courseCodes) {
      Course course = courseRepository.findByCode(courseCode);
      if (course == null) {
        System.out.println("강의가 존재하지 않습니다. 강의코드: " + courseCode);
        continue;
      }
      registrationRepository.register(userId, course.getId());
    }
  }

  public UserAcademicStatusDTO getUserAcademicStatus(String userId) {
    // TODO: Implement this method
    // Mock data

    int totalRequiredCredit = 130;
    int totalCompletedCredit = 100;
    int majorRequiredCredit = 70;
    int majorCompletedCredit = 60;
    int liberalRequiredCredit = 30;
    int liberalCompletedCredit = 20;
    int engineeringRequiredCredit = 8;
    int engineeringCompletedCredit = 6;
    int generalRequiredCredit = 30;
    int generalCompletedCredit = 20;
    double minimumTotalGradePointAverage = 2.0;
    double totalGradePointAverage = 3.5;

    return new UserAcademicStatusDTO(
        totalRequiredCredit,
        totalCompletedCredit,
        majorRequiredCredit,
        majorCompletedCredit,
        liberalRequiredCredit,
        liberalCompletedCredit,
        engineeringRequiredCredit,
        engineeringCompletedCredit,
        generalRequiredCredit,
        generalCompletedCredit,
        minimumTotalGradePointAverage,
        totalGradePointAverage);
  }
}

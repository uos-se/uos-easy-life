package kr.ac.uos.uos_easy_life.core.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.ac.uos.uos_easy_life.core.interfaces.AcademicStatusRepository;
import kr.ac.uos.uos_easy_life.core.interfaces.CourseRepository;
import kr.ac.uos.uos_easy_life.core.interfaces.RegistrationRepository;
import kr.ac.uos.uos_easy_life.core.interfaces.UosApi;
import kr.ac.uos.uos_easy_life.core.interfaces.UosSessionManager;
import kr.ac.uos.uos_easy_life.core.interfaces.UserRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.User;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatusDTO;
import kr.ac.uos.uos_easy_life.core.model.UserFullInfo;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final RegistrationRepository registrationRepository;
  private final UosSessionManager uosSessionManager;
  private final AcademicStatusRepository academicStatusRepository;
  private final UosApi uosApi;

  public UserService(UserRepository userRepository, CourseRepository courseRepository,
      RegistrationRepository registrationRepository, UosSessionManager uosSessionManager,
      AcademicStatusRepository academicStatusRepository,
      UosApi uosApi) {
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.registrationRepository = registrationRepository;
    this.uosSessionManager = uosSessionManager;
    this.academicStatusRepository = academicStatusRepository;
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
        user.getDepartment().getDepartmentName(), user.getCurrentGrade(), "", courses,
        user.getUpdatedAt());
  }

  public void syncUser(String userId, String portalId, String portalPassword) {
    // Validate user
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
    }
    if (!user.checkPassword(portalPassword)) {
      throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
    }

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

    // Sync academic status
    UserAcademicStatus academicStatus = uosApi.getUserAcademicStatus(session, user.getName(), user.getStudentId());
    if (academicStatus == null) {
      throw new IllegalArgumentException("학적 정보 동기화에 실패했습니다.");
    }
    academicStatusRepository.setAcademicStatus(userId, academicStatus);
  }

  public UserAcademicStatusDTO getUserAcademicStatus(String userId) {
    UserAcademicStatus academicStatus = academicStatusRepository.getAcademicStatus(userId);
    if (academicStatus == null) {
      throw new IllegalArgumentException("학적 정보가 존재하지 않습니다.");
    }

    int totalRequiredCredit = 130;
    int totalCompletedCredit = academicStatus.getTotalCompletedCredit();

    int majorRequiredCredit = 72;
    int majorCompletedCredit = academicStatus.getMajorCompletedCredit();

    int majorEssentialRequiredCredit = 24;
    int majorEssentialCompletedCredit = academicStatus.getMajorEssentialCompletedCredit();

    int liberalRequiredCredit = 36;
    int liberalCompletedCredit = academicStatus.getLiberalCompletedCredit();

    int liberalEssentialRequiredCredit = 14;
    int liberalEssentialCompletedCredit = academicStatus.getLiberalEssentialCompletedCredit();

    int engineeringRequiredCredit = 8;
    int engineeringCompletedCredit = academicStatus.getEngineeringCompletedCredit();

    // generalCredit(일반선택)은 요구 학점이 없다. 대충 현재학점==
    int generalRequiredCredit = academicStatus.getGeneralCompletedCredit();
    int generalCompletedCredit = academicStatus.getGeneralCompletedCredit();

    double minimumTotalGradePointAverage = 2.0;
    double totalGradePointAverage = academicStatus.getTotalGradePointAverage();

    return new UserAcademicStatusDTO(
        totalRequiredCredit,
        totalCompletedCredit,

        majorRequiredCredit,
        majorCompletedCredit,

        majorEssentialRequiredCredit,
        majorEssentialCompletedCredit,

        liberalRequiredCredit,
        liberalCompletedCredit,

        liberalEssentialRequiredCredit,
        liberalEssentialCompletedCredit,

        engineeringRequiredCredit,
        engineeringCompletedCredit,

        generalRequiredCredit,
        generalCompletedCredit,

        minimumTotalGradePointAverage,
        totalGradePointAverage);
  }

  public List<Course> getRecommendedCourses(String userId) {
    UserAcademicStatus academicStatus = academicStatusRepository.getAcademicStatus(userId);
    if (academicStatus == null) {
      throw new IllegalArgumentException("학적 정보가 존재하지 않습니다.");
    }

    List<Course> recommendedCourses = new ArrayList<>();
    List<Course> allCourses = courseRepository.findAll();

    // TODO: Implement here
    // Select just 20 random courses for mocking
    for (int i = 0; i < 20; i++) {
      recommendedCourses.add(allCourses.get(i));
    }

    return recommendedCourses;
  }
}

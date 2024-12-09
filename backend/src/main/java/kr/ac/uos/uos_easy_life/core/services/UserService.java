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
import kr.ac.uos.uos_easy_life.core.model.UserInfo;

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

    // Sync user info
    UserInfo userInfo = uosApi.getUserInfo(session, user.getStudentId());
    if (userInfo == null) {
      throw new IllegalArgumentException("사용자 정보 동기화에 실패했습니다.");
    }
    user.setCurrentGrade(userInfo.getGrade());
    user.setCurrentSemester(userInfo.getSemester());
    userRepository.update(user);

    // Sync courses
    List<String> courseCodes = uosApi.getUserCourseCodes(session, user.getStudentId());
    for (String courseCode : courseCodes) {
      /*
       * TODO: 이 부분에서 강의 코드는 같지만 다른 학과의 강의가 검색되는 경우가 있다. 이 이슈 해결해야 함.
       * 
       * 이 이슈는 findByCode에서 단순히 코드가 매칭되는 번째 강의를 반환하는 것이 아니라 매칭되는 모든 강의를 반환하도록 한 후 여기에서
       * 비즈니스 로직으로 학과 정보 등을 고려하여 필터링하도록 수정해야 한다. 이를 위해서는 uosApi.getUserCourseCodes를
       * 단순히 course code 뿐만 아니라 강의를 구체적으로 결정할 수 있는 정보를 포함하도록 수정해야 한다.
       * 
       * Registration에 단순히 강의 코드만을 저장하여 해결할 수도 있지만, 이렇게 하면 연도에 따라 동일한 강의이지만 수강 학점이
       * 달라지거나 연도에 따라 전공필수/공학필수 등 여부가 달라지는 등의 edge-case에 대응할 수 없게 되므로 추후 확장성을 위해 이
       * 방식으로 해결하지는 않기로 한다.
       */
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
    User user = userRepository.findById(userId);
    UserAcademicStatus academicStatus = academicStatusRepository.getAcademicStatus(userId);
    if (academicStatus == null) {
      throw new IllegalArgumentException("학적 정보가 존재하지 않습니다.");
    }

    CoursePlanner coursePlanner = new CoursePlanner(courseRepository, registrationRepository);
    List<Course> recommendedCourses = coursePlanner.planCourses(user, academicStatus);
    recommendedCourses.removeIf(course -> course == null);
    return recommendedCourses;
  }
}

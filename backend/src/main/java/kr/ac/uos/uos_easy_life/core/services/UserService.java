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

  /**
   * 학년/학기별 전공필수과목 반환
   * 
   * @param grade
   * @param semester
   * @param majorCompletedCredit
   * @return
   */
  private List<Course> getRecommendedMajorEssentialCourses(int grade, int semester, int majorCompletedCredit) {
    List<Course> recommendedCourses = new ArrayList<>();

    if (grade == 1 && semester == 2) {
      recommendedCourses.add(courseRepository.findByName("창의공학기초설계"));
      recommendedCourses.add(courseRepository.findByName("C언어및실습"));
    } else if (grade == 2 && semester == 1) {
      recommendedCourses.add(courseRepository.findByName("논리회로설계및실습"));
      recommendedCourses.add(courseRepository.findByName("이산수학"));
    } else if (grade == 2 && semester == 2) {
      recommendedCourses.add(courseRepository.findByName("자료구조"));
    } else if (grade == 3 && semester == 1) {
      recommendedCourses.add(courseRepository.findByName("운영체제"));
    } else if (grade == 3 && semester == 2) {
      recommendedCourses.add(courseRepository.findByName("소프트웨어공학"));
    } else if (grade == 4 && semester == 1) {
      recommendedCourses.add(courseRepository.findByName("컴퓨터과학종합설계"));
    } else if (grade == 4 && semester == 2 && majorCompletedCredit < 24) {
      recommendedCourses.add(courseRepository.findByName("컴퓨터과학종합설계"));
    }
    return recommendedCourses;
  }

  /**
   * 학년/학기별 공학소양선택과목 시수 반환
   * 
   * @param grade
   * @param semester
   * @param majorCompletedCredit
   * @return
   */
  private static int getEngineeringCredits(int grade, int semester, int completedEngineering) {
    if (grade == 1 && semester == 2) {
      return 1;
    }

    if (grade == 2 && completedEngineering >= 2) {
      return 2;
    }

    if (grade == 3 && completedEngineering < 4) {
      return 2;
    }

    if (grade == 3 && completedEngineering >= 4) {
      return 1;
    }

    if (grade == 4) {
      return (int) Math.ceil((8 - completedEngineering) / 2);
    }

    return 0;
  }

  private static int random(int max) {
    return (int) (Math.random() * (max + 1));
  }

  private static List<Course> sampleCourses(Course[] courses, int count) {
    if (courses.length <= count) {
      throw new IllegalArgumentException("과목 개수가 부족합니다.");
    }

    // 겹치지 않게 랜덤하게 count개의 과목을 추출
    List<Course> result = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      int index = random(courses.length - 1);
      if (!result.contains(courses[index])) {
        result.add(courses[index]);
      } else {
        i--;
      }
    }
    return result;
  }

  private List<Course> getRecommendedLiberalElective(int grade, int semester) {
    // 1학년 1학기에는 기초과학선택 중 적당히 두 개 추천
    if (grade == 1 && semester == 1) {
      Course[] courses = new Course[] {
          courseRepository.findByName("일반화학및실험 I"),
          courseRepository.findByName("일반물리및실험 I"),
          courseRepository.findByName("일반생물및실험 I")
      };
      return sampleCourses(courses, 2);
    }

    // 1학년 2학기에는 기초선택과목 II 중 적당히 두 개 추천
    // TODO: 1학년 1학기 때 들은 과목과 연계 필요
    if (grade == 1 && semester == 2) {
      Course[] courses = new Course[] {
          courseRepository.findByName("일반화학및실험 II"),
          courseRepository.findByName("일반물리및실험 II"),
          courseRepository.findByName("일반생물및실험 II")
      };
      return sampleCourses(courses, 2);
    }

    // 아무 해당 없으면 빈 리스트 반환
    return new ArrayList<>();
  }

  private static int getRecommendedMajorElectiveCredits(int grade) {
    if (grade > 1) {
      return 5;
    } else {
      return 0;
    }
  }

  private static int getRecommendedLiberalElectiveCredits(int grade) {
    if (grade == 1) {
      return 0;
    } else if (grade == 2) {
      return 3;
    } else if (grade == 3) {
      return 2;
    } else {
      return 1;
    }
  }

  public List<Course> getRecommendedCourses(String userId) {
    User user = userRepository.findById(userId);
    UserAcademicStatus academicStatus = academicStatusRepository.getAcademicStatus(userId);
    if (academicStatus == null) {
      throw new IllegalArgumentException("학적 정보가 존재하지 않습니다.");
    }

    List<Course> recommendedCourses = new ArrayList<>();

    if (user.getCurrentGrade() == 1 && user.getCurrentSemester() == 1) {
      recommendedCourses.add(courseRepository.findByName("UOS미래디자인"));
      recommendedCourses.add(courseRepository.findByName("의사결정과토론"));
      recommendedCourses.add(courseRepository.findByName("대학영어(W)"));
      recommendedCourses.add(courseRepository.findByName("수학I"));
      recommendedCourses.add(courseRepository.findByName("물리학및실험I"));
      recommendedCourses.add(courseRepository.findByName("화학및실험I"));
      recommendedCourses.add(courseRepository.findByName("생물학및실험I"));
      recommendedCourses.add(courseRepository.findByName("컴퓨터과학개론"));
      recommendedCourses.add(courseRepository.findByName("프로그래밍입문"));
      recommendedCourses.removeIf(course -> course == null);
      return recommendedCourses;
    }

    if (user.getCurrentGrade() == 1 && user.getCurrentSemester() == 2) {
      recommendedCourses.add(courseRepository.findByName("과학기술글쓰기"));
      recommendedCourses.add(courseRepository.findByName("대학영어(S)"));
      recommendedCourses.add(courseRepository.findByName("물리학및실험II"));
      recommendedCourses.add(courseRepository.findByName("화학및실험II"));
      recommendedCourses.add(courseRepository.findByName("생물학및실험II"));
      recommendedCourses.add(courseRepository.findByName("수학II"));
      recommendedCourses.add(courseRepository.findByName("창의공학기초설계"));
      recommendedCourses.add(courseRepository.findByName("C언어및실습"));
      recommendedCourses.add(courseRepository.findByName("공학도의창업과경영"));
      recommendedCourses.removeIf(course -> course == null);
      return recommendedCourses;
    }

    // 전필
    recommendedCourses.addAll(getRecommendedMajorEssentialCourses(
        user.getCurrentGrade(),
        user.getCurrentSemester(),
        academicStatus.getMajorCompletedCredit()));

    // 교필
    int liberalEssentialRequiredCredit;
    if (user.getStudentId().startsWith("2023") ||
        user.getStudentId().startsWith("2024")) {
      liberalEssentialRequiredCredit = Math.max(0, 15 - academicStatus.getLiberalEssentialCompletedCredit());
    } else {
      liberalEssentialRequiredCredit = Math.max(0, 14 - academicStatus.getLiberalEssentialCompletedCredit());
    }

    // 공소
    recommendedCourses.addAll(getRecommendedLiberalElective(user.getCurrentGrade(), user.getCurrentSemester()));

    // 전선

    // 교선

    recommendedCourses.removeIf(course -> course == null);
    return recommendedCourses;
  }
}

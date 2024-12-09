package kr.ac.uos.uos_easy_life.core.services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.ac.uos.uos_easy_life.core.interfaces.CourseRepository;
import kr.ac.uos.uos_easy_life.core.interfaces.RegistrationRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.Department;
import kr.ac.uos.uos_easy_life.core.model.User;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;
import kr.ac.uos.uos_easy_life.infra.JsonCourseRepository;

public class CoursePlannerTest {

  static CourseRepository courseRepository;
  @Mock
  RegistrationRepository registrationRepository;
  CoursePlanner coursePlanner;
  static int currentYear;

  @BeforeAll
  static void init() throws IOException {
    courseRepository = new JsonCourseRepository();
    currentYear = LocalDate.now().getYear();
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("planCoursesTestSource")
  void planCoursesTest(int grade, int semester, int majorEssentialCompletedCredit,
      List<String> registeredCourseIds, List<String> recommendedCourseIds) {
    // given
    User user = mock(User.class);
    UserAcademicStatus userAcademicStatus = mock(UserAcademicStatus.class);
    when(user.getId()).thenReturn("1");
    when(user.getCurrentGrade()).thenReturn(grade);
    when(user.getCurrentSemester()).thenReturn(semester);
    when(userAcademicStatus.getMajorCompletedCredit()).thenReturn(majorEssentialCompletedCredit);
    when(registrationRepository.findRegisteredCourses(anyString())).thenReturn(registeredCourseIds);
    coursePlanner = new CoursePlanner(courseRepository, registrationRepository);

    // when
    List<Course> planCourses = coursePlanner.planCourses(user, userAcademicStatus);

    // then
    // filter test
    // 중복된 과목이 없어야 함
    assertThat(planCourses).doesNotHaveDuplicates();
    // 수강한 과목이 없어야 함
    assertThat(planCourses).noneMatch(course -> registeredCourseIds.contains(course.getId()));
    // 마지막으로 개설된 연도가 2년 이상인 과목이 없어야 함
    assertThat(planCourses).noneMatch(course -> course.getLastOpenYear() <= currentYear - 2);

    // 과목 추천 테스트
    assertThat(planCourses.size()).isEqualTo(recommendedCourseIds.size());
    planCourses.forEach(course -> assertThat(recommendedCourseIds).contains(course.getId()));
    recommendedCourseIds
        .forEach(courseId -> assertThat(planCourses).anyMatch(course -> course.getId().equals(courseId)));
  }

  static Stream<Arguments> planCoursesTestSource() {

    // 수강리스트를 다음과 같이 가정
    List<String> registeredCourseIds = Arrays.asList(
        courseRepository.findByNameAndDepartment("고전과함께하는비판적토론", Department.CommunicationClass).getId(),
        courseRepository.findByNameAndDepartment("대학영어(W)", Department.GeneralEnglish).getId(),
        courseRepository.findByNameAndDepartment("수학 I", Department.GeneralMath).getId(),
        courseRepository.findByNameAndDepartment("물리학및실험1", Department.GeneralPhysics).getId(),
        courseRepository.findByNameAndDepartment("컴퓨터과학개론", Department.ComputerScience).getId(),
        courseRepository.findByNameAndDepartment("프로그래밍입문", Department.ComputerScience).getId(),
        courseRepository.findByNameAndDepartment("UOS미래디자인", Department.GeneralEducation).getId(),
        courseRepository.findByNameAndDepartment("자료구조", Department.ComputerScience).getId());

    // 컴퓨터과학부 전공과목
    List<String> recommendedCourseIds = new ArrayList<>(
        courseRepository.findByDepartment(Department.ComputerScience).stream()
            .map(Course::getId).toList());

    // 수강리스트 반영
    recommendedCourseIds.removeAll(registeredCourseIds);
    // 2년이상된 과목 제거
    recommendedCourseIds.removeIf(courseId -> courseRepository.findById(courseId).getLastOpenYear() <= currentYear - 2);

    // 1학년 1학기는 하드코딩된 과목
    List<String> recommendedCourseIds1by1 = List.of(
        courseRepository.findByNameAndDepartment("과학기술글쓰기", Department.CommunicationClass).getId(),
        courseRepository.findByNameAndDepartment("대학영어(S)", Department.GeneralEnglish).getId(),
        courseRepository.findByNameAndDepartment("물리학및실험2", Department.GeneralPhysics).getId(),
        courseRepository.findByNameAndDepartment("화학및실험2", Department.GeneralChemistry).getId(),
        courseRepository.findByNameAndDepartment("생물학및실험2", Department.GeneralBiology).getId(),
        courseRepository.findByNameAndDepartment("수학 II", Department.GeneralMath).getId(),
        courseRepository.findByNameAndDepartment("창의공학기초설계", Department.ComputerScience).getId(),
        courseRepository.findByNameAndDepartment("C언어및실습", Department.ComputerScience).getId(),
        courseRepository.findByNameAndDepartment("공학도의창업과경영", Department.GeneralEducation).getId());

    return Stream.of(
        Arguments.of(1, 1, 9, registeredCourseIds, recommendedCourseIds1by1),
        Arguments.of(1, 2, 9, registeredCourseIds, recommendedCourseIds),
        Arguments.of(2, 1, 9, registeredCourseIds, recommendedCourseIds),
        Arguments.of(2, 2, 9, registeredCourseIds, recommendedCourseIds),
        Arguments.of(3, 1, 9, registeredCourseIds, recommendedCourseIds),
        Arguments.of(3, 2, 9, registeredCourseIds, recommendedCourseIds),
        Arguments.of(4, 1, 9, registeredCourseIds, recommendedCourseIds),
        Arguments.of(4, 1, 24, registeredCourseIds, recommendedCourseIds));
  }

}

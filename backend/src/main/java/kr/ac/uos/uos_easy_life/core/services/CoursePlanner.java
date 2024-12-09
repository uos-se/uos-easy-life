package kr.ac.uos.uos_easy_life.core.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.ac.uos.uos_easy_life.core.interfaces.CourseRepository;
import kr.ac.uos.uos_easy_life.core.interfaces.RegistrationRepository;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.Department;
import kr.ac.uos.uos_easy_life.core.model.User;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;

public class CoursePlanner {
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    public CoursePlanner(CourseRepository courseRepository, RegistrationRepository registrationRepository) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
    }

    private List<Course> filterCourses(User user, List<Course> courses) {
        /*
         * 중복 제거를 위해 이미 수강한 과목 코드 저장
         * NOTE: 같은 Course code를 가진 타과 강의가 있기도 하다. 그래서 이것이 매칭되지 않도록 Course id 대신 course
         * code로 체크한다. 이것이 필요한 이유는 일차적으로는 지금 UserService에서 로직상의 오류로 registration에 과목 코드가
         * 동일한 타 학과 과목이 들어가있는 경우가 있기 때문이다.
         * 
         * 그러나 나중에 이 오류가 해결되더라도 course code 기반 매칭은 필요하다. 왜냐하면 지금은 planning 시 수강 가능한 타과
         * 전공이나 타과 교양을 추천하지 않지만 나중에 타과의 수강 가능한 전공 등 과목을 추천하는 등 확장하게 되면 course id 기반의
         * 필터링이 필요하기 때문이다.
         */
        Set<String> filteredCourseCodes = new HashSet<>();
        List<String> registeredCourseIds = registrationRepository.findRegisteredCourses(user.getId());
        for (String courseId : registeredCourseIds) {
            Course course = courseRepository.findById(courseId);
            if (course != null) {
                filteredCourseCodes.add(course.getLectureCode());
            }
        }

        // 기수강 과목과 주어진 courses 리스트 내에서 중복되는 과목, 최근 2년 이상 개설되지 않은 과목 제거
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses) {
            if (registeredCourseIds.contains(course.getId())) {
                continue;
            }
            if (filteredCourseCodes.contains(course.getLectureCode())) {
                continue;
            }
            // 마지막으로 개설된 연도가 2년 이상인 과목 제거
            int currentYear = LocalDate.now().getYear();
            if (course.getLastOpenYear() <= currentYear - 2) {
                continue;
            }
            filteredCourses.add(course);
            filteredCourseCodes.add(course.getLectureCode());
        }
        return filteredCourses;
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
            recommendedCourses.add(courseRepository.findByNameAndDepartment("창의공학기초설계", Department.ComputerScience));
            recommendedCourses.add(courseRepository.findByNameAndDepartment("C언어및실습", Department.ComputerScience));
        } else if (grade == 2 && semester == 1) {
            recommendedCourses.add(courseRepository.findByNameAndDepartment("논리회로및실습", Department.ComputerScience));
            recommendedCourses.add(courseRepository.findByNameAndDepartment("이산수학", Department.ComputerScience));
        } else if (grade == 2 && semester == 2) {
            recommendedCourses.add(courseRepository.findByNameAndDepartment("자료구조", Department.ComputerScience));
        } else if (grade == 3 && semester == 1) {
            recommendedCourses.add(courseRepository.findByNameAndDepartment("운영체제", Department.ComputerScience));
        } else if (grade == 3 && semester == 2) {
            recommendedCourses.add(courseRepository.findByNameAndDepartment("소프트웨어공학", Department.ComputerScience));
        } else if (grade == 4 && semester == 1) {
            recommendedCourses.add(courseRepository.findByNameAndDepartment("컴퓨터과학종합설계", Department.ComputerScience));
        } else if (grade == 4 && semester == 2 && majorCompletedCredit < 24) {
            recommendedCourses.add(courseRepository.findByNameAndDepartment("컴퓨터과학종합설계", Department.ComputerScience));
        }
        return recommendedCourses;
    }

    /**
     * 학년, 학기별 교양필수과목 추천
     * 
     * @param grade
     * @param semester
     * @return
     */
    private List<Course> getRecommendedLiberalEssential(int grade, int semester) {
        if (grade == 1 && semester == 1) {
            List<Course> courses = new ArrayList<>();
            courses.add(courseRepository.findByNameAndDepartment("물리학및실험1", Department.GeneralPhysics));
            courses.add(courseRepository.findByNameAndDepartment("화학및실험1", Department.GeneralChemistry));
            courses.add(courseRepository.findByNameAndDepartment("생물학및실험1", Department.GeneralBiology));
            return courses;
        }

        if (grade == 1 && semester == 2) {
            List<Course> courses = new ArrayList<>();
            courses.add(courseRepository.findByNameAndDepartment("물리학및실험2", Department.GeneralPhysics));
            courses.add(courseRepository.findByNameAndDepartment("화학및실험2", Department.GeneralChemistry));
            courses.add(courseRepository.findByNameAndDepartment("생물학및실험2", Department.GeneralBiology));
            return courses;
        }

        // 아무 해당 없으면 빈 리스트 반환
        return new ArrayList<>();
    }

    public List<Course> planCourses(User user, UserAcademicStatus userAcademicStatus) {

        // 1학년의 경우 hard-coded 시간표 반환

        if (user.getCurrentGrade() == 1 && user.getCurrentSemester() == 1) {
            List<Course> courses = new ArrayList<>();
            courses.add(courseRepository.findByNameAndDepartment("고전과함께하는비판적토론", Department.CommunicationClass));
            courses.add(courseRepository.findByNameAndDepartment("대학영어(W)", Department.GeneralEnglish));
            courses.add(courseRepository.findByNameAndDepartment("수학 I", Department.GeneralMath));
            courses.add(courseRepository.findByNameAndDepartment("물리학및실험1", Department.GeneralPhysics));
            courses.add(courseRepository.findByNameAndDepartment("화학및실험1", Department.GeneralChemistry));
            courses.add(courseRepository.findByNameAndDepartment("생물학및실험1", Department.GeneralBiology));
            courses.add(courseRepository.findByNameAndDepartment("컴퓨터과학개론", Department.ComputerScience));
            courses.add(courseRepository.findByNameAndDepartment("프로그래밍입문", Department.ComputerScience));
            courses.add(courseRepository.findByNameAndDepartment("UOS미래디자인", Department.GeneralEducation));
            courses.removeIf(course -> course == null);
            return courses;
        }

        if (user.getCurrentGrade() == 1 && user.getCurrentSemester() == 2) {
            List<Course> courses = new ArrayList<>();
            courses.add(courseRepository.findByNameAndDepartment("과학기술글쓰기", Department.CommunicationClass));
            courses.add(courseRepository.findByNameAndDepartment("대학영어(S)", Department.GeneralEnglish));
            courses.add(courseRepository.findByNameAndDepartment("물리학및실험2", Department.GeneralPhysics));
            courses.add(courseRepository.findByNameAndDepartment("화학및실험2", Department.GeneralChemistry));
            courses.add(courseRepository.findByNameAndDepartment("생물학및실험2", Department.GeneralBiology));
            courses.add(courseRepository.findByNameAndDepartment("수학 II", Department.GeneralMath));
            courses.add(courseRepository.findByNameAndDepartment("창의공학기초설계", Department.ComputerScience));
            courses.add(courseRepository.findByNameAndDepartment("C언어및실습", Department.ComputerScience));
            courses.add(courseRepository.findByNameAndDepartment("공학도의창업과경영", Department.GeneralEducation));
            courses.removeIf(course -> course == null);
            return courses;
        }

        List<Course> courses = new ArrayList<>();

        courses.addAll(getRecommendedMajorEssentialCourses(
                user.getCurrentGrade(),
                user.getCurrentSemester(),
                userAcademicStatus.getMajorCompletedCredit()));

        courses.addAll(getRecommendedLiberalEssential(
                user.getCurrentGrade(),
                user.getCurrentSemester()));

        // 아몰랑 컴퓨터과학부 수업 전부 추가~~
        courses.addAll(courseRepository.findByDepartment(Department.ComputerScience));

        // 필터링 (중복 제거, 이미 수강한 과목 제거, 마지막으로 개설된 연도가 2년 이상인 과목 제거)
        courses = filterCourses(user, courses);

        return courses;
    }
}

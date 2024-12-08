package kr.ac.uos.uos_easy_life.core.services;

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
        List<Course> filteredCourses = new ArrayList<>();
        Set<String> filteredCourseIds = new HashSet<>();
        List<String> registeredCourseIds = registrationRepository.findRegisteredCourses(user.getId());
        for (Course course : courses) {
            if (registeredCourseIds.contains(course.getId())) {
                continue;
            }
            if (filteredCourseIds.contains(course.getId())) {
                continue;
            }
            filteredCourses.add(course);
            filteredCourseIds.add(course.getId());
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
            recommendedCourses.add(courseRepository.findByName("창의공학기초설계"));
            recommendedCourses.add(courseRepository.findByName("C언어및실습"));
        } else if (grade == 2 && semester == 1) {
            recommendedCourses.add(courseRepository.findByName("논리회로및실습"));
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
     * 학년, 학기별 교양필수과목 추천
     * 
     * @param grade
     * @param semester
     * @return
     */
    private List<Course> getRecommendedLiberalEssential(int grade, int semester) {
        if (grade == 1 && semester == 1) {
            List<Course> courses = new ArrayList<>();
            courses.add(courseRepository.findByName("화학및실험1"));
            courses.add(courseRepository.findByName("물리학및실험1"));
            courses.add(courseRepository.findByName("생물학및실험1"));
            return courses;
        }

        if (grade == 1 && semester == 2) {
            List<Course> courses = new ArrayList<>();
            courses.add(courseRepository.findByName("화학및실험2"));
            courses.add(courseRepository.findByName("물리학및실험2"));
            courses.add(courseRepository.findByName("생물학및실험2"));
            return courses;
        }

        // 아무 해당 없으면 빈 리스트 반환
        return new ArrayList<>();
    }

    public List<Course> planCourses(User user, UserAcademicStatus userAcademicStatus) {

        // 1학년의 경우 hard-coded 시간표 반환

        if (user.getCurrentGrade() == 1 && user.getCurrentSemester() == 1) {
            List<Course> courses = new ArrayList<>();
            courses.add(courseRepository.findByName("UOS미래디자인"));
            courses.add(courseRepository.findByName("의사결정과토론"));
            courses.add(courseRepository.findByName("대학영어(W)"));
            courses.add(courseRepository.findByName("대학수학I"));
            courses.add(courseRepository.findByName("물리학및실험1"));
            courses.add(courseRepository.findByName("화학및실험1"));
            courses.add(courseRepository.findByName("생물학및실험1"));
            courses.add(courseRepository.findByName("컴퓨터과학개론"));
            courses.add(courseRepository.findByName("프로그래밍입문"));
            courses.removeIf(course -> course == null);
            return courses;
        }

        if (user.getCurrentGrade() == 1 && user.getCurrentSemester() == 2) {
            List<Course> courses = new ArrayList<>();
            courses.add(courseRepository.findByName("과학기술글쓰기"));
            courses.add(courseRepository.findByName("대학영어(S)"));
            courses.add(courseRepository.findByName("물리학및실험2"));
            courses.add(courseRepository.findByName("화학및실험2"));
            courses.add(courseRepository.findByName("생물학및실험2"));
            courses.add(courseRepository.findByName("대학수학II"));
            courses.add(courseRepository.findByName("창의공학기초설계"));
            courses.add(courseRepository.findByName("C언어및실습"));
            courses.add(courseRepository.findByName("공학도의창업과경영"));
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
        Department cs = Department.fromDepartmentCode(92);
        courses.addAll(courseRepository.findByDepartment(cs));

        // 필터링 (중복 제거, 이미 수강한 과목 제거)
        courses = filterCourses(user, courses);

        return courses;
    }
}

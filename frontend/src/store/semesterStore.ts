import { Course } from "@/api/models/Course";
import { create } from "zustand";

interface Semester {
  id: string;
  courses: Course[];
}

interface SemesterStore {
  semesters: Semester[];
  courses: Course[];
  addCourseToSemester: (semesterId: string, course: Course) => void;
  removeCourseFromSemester: (semesterId: string, courseId: string) => void;
  isInUse: (course: Course) => boolean;
}

const dummyCourses: Course[] = [
  {
    id: "1",
    lectureName: "프로그래밍 기초",
    lectureCode: "CS101",
    lectureCredit: 3,
    lectureGrade: 1,
  },
  {
    id: "2",
    lectureName: "자료구조",
    lectureCode: "CS102",
    lectureCredit: 3,
    lectureGrade: 2,
  },
  {
    id: "3",
    lectureName: "알고리즘",
    lectureCode: "CS201",
    lectureCredit: 3,
    lectureGrade: 2,
  },
  {
    id: "4",
    lectureName: "운영체제",
    lectureCode: "CS202",
    lectureCredit: 3,
    lectureGrade: 3,
  },
  {
    id: "5",
    lectureName: "데이터베이스",
    lectureCode: "CS301",
    lectureCredit: 3,
    lectureGrade: 3,
  },
  {
    id: "6",
    lectureName: "자료구조",
    lectureCode: "CS102",
    lectureCredit: 3,
    lectureGrade: 2,
  },
  {
    id: "7",
    lectureName: "알고리즘",
    lectureCode: "CS201",
    lectureCredit: 3,
    lectureGrade: 2,
  },
  {
    id: "8",
    lectureName: "운영체제",
    lectureCode: "CS202",
    lectureCredit: 3,
    lectureGrade: 3,
  },
  {
    id: "9",
    lectureName: "데이터베이스",
    lectureCode: "CS301",
    lectureCredit: 3,
    lectureGrade: 3,
  },
];

export const useSemesterStore = create<SemesterStore>((set, get) => ({
  semesters: [
    { id: "1", courses: [] },
    { id: "2", courses: [] },
    { id: "3", courses: [] },
  ],
  courses: dummyCourses,
  fetch: () => {
    set(() => ({
      courses: [...dummyCourses],
    }));
  },
  addCourseToSemester: (semesterId, course) =>
    set((state) => ({
      semesters: state.semesters.map((semester) =>
        semester.id === semesterId
          ? { ...semester, courses: [...semester.courses, course] }
          : semester
      ),
    })),
  removeCourseFromSemester: (semesterId, courseId) =>
    set((state) => ({
      semesters: state.semesters.map((semester) =>
        semester.id === semesterId
          ? {
              ...semester,
              courses: semester.courses.filter(
                (course) => course.id !== courseId
              ),
            }
          : semester
      ),
    })),
  isInUse: (course) => {
    const state = get();
    const isInUse = state.semesters
      .map((semester) => {
        const index = semester.courses.find((c) => c.id == course.id);
        return index !== undefined;
      })
      .reduce((prev, curr) => prev || curr, false);
    return isInUse;
  },
}));

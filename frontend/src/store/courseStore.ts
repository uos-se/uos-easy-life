import { Course } from "@/types/Course";
import { create } from "zustand";

interface CourseStore {
  courses: Course[];
  filteredCourses: Course[];
  setCourses: (courses: Course[]) => void;
  filterCourses: (criteria: string) => void;
}

export const useCourseStore = create<CourseStore>((set) => ({
  courses: [
    {
      id: "1",
      lectureName: "데이터구조",
      lectureCode: "CS2001",
      lectureCredit: 2,
      lectureEngineeringCredit: 4.0,
      isMajor: true,
      isMajorEssential: true,
    },
    {
      id: "2",
      lectureName: "알고리즘",
      lectureCode: "CS3001",
      lectureCredit: 3,
      lectureEngineeringCredit: 3.5,
      isMajor: true,
      isMajorEssential: true,
    },
    {
      id: "3",
      lectureName: "운영체제",
      lectureCode: "CS3002",
      lectureCredit: 3,
      lectureEngineeringCredit: 4.0,
      isMajor: true,
      isMajorEssential: false,
    },
    {
      id: "4",
      lectureName: "데이터베이스",
      lectureCode: "CS4001",
      lectureCredit: 3,
      lectureEngineeringCredit: 3.5,
      isMajor: true,
      isMajorEssential: false,
    },
    {
      id: "5",
      lectureName: "컴퓨터네트워크",
      lectureCode: "CS4002",
      lectureCredit: 3,
      lectureEngineeringCredit: 4.0,
      isMajor: false,
      isMajorEssential: false,
    },
  ],
  filteredCourses: [
    {
      id: "1",
      lectureName: "데이터구조",
      lectureCode: "CS2001",
      lectureCredit: 2,
      lectureEngineeringCredit: 4.0,
      isMajor: true,
      isMajorEssential: true,
    },
    {
      id: "2",
      lectureName: "알고리즘",
      lectureCode: "CS3001",
      lectureCredit: 3,
      lectureEngineeringCredit: 3.5,
      isMajor: true,
      isMajorEssential: true,
    },
    {
      id: "3",
      lectureName: "운영체제",
      lectureCode: "CS3002",
      lectureCredit: 3,
      lectureEngineeringCredit: 4.0,
      isMajor: true,
      isMajorEssential: false,
    },
    {
      id: "4",
      lectureName: "데이터베이스",
      lectureCode: "CS4001",
      lectureCredit: 3,
      lectureEngineeringCredit: 3.5,
      isMajor: true,
      isMajorEssential: false,
    },
    {
      id: "5",
      lectureName: "컴퓨터네트워크",
      lectureCode: "CS4002",
      lectureCredit: 3,
      lectureEngineeringCredit: 4.0,
      isMajor: true,
      isMajorEssential: false,
    },
  ],
  setCourses: (courses) => set({ courses, filteredCourses: courses }),
  filterCourses: () =>
    set((state) => ({
      filteredCourses: state.courses.sort(
        (a, b) => a.lectureCredit - b.lectureCredit
      ),
    })),
}));

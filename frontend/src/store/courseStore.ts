import { Course } from "@/api/models/Course";
import { create } from "zustand";

export interface CourseStore {
  recommendedCourses?: Course[];
  fetchRecommendedCourses: () => void;
}

export const useCourseStore = create<CourseStore>((set) => ({
  fetchRecommendedCourses: async () => {
    set({
      recommendedCourses: [
        {
          id: "",
          lectureCode: "CSE 101",
          lectureCredit: 3,
          lectureGrade: 1,
          lectureName: "Introduction to Computer Science",
        },
      ],
    });
  },
}));

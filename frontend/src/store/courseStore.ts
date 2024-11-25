import { Course } from "@/models/Course";
import { create } from "zustand";

export interface CourseStore {
  recommendedCourses?: Course[];
  fetchRecommendedCourses: (criteria: string) => void;
}

export const useCourseStore = create<CourseStore>((set) => ({
  fetchRecommendedCourses: async (criteria: string) => {
    // const session = getSession()!;
    // No Course API yet
    // const courses = ControllerService.getUserAcademicStatus(session.key);
    // set({
    //   takenCourses: courses,
    // });
  },
}));

import { Course } from "@/api/models/Course";
import { create } from "zustand";

export interface CourseStore {
  recommendedCourses?: Course[];
  fetchRecommendedCourses: (criteria: string) => void;
}

export const useCourseStore = create<CourseStore>((set) => ({
  recommendedCourses: [],
  fetchRecommendedCourses: async () => {
    set({ recommendedCourses: [] });
  },
}));

import { ControllerService, CoursePlan } from "@/api";
import { Course } from "@/api/models/Course";
import { v4 as uuidv4 } from "uuid";
import { create } from "zustand";
import { getSession } from "./userStore";

interface SemesterStore {
  semesters?: CoursePlan[];
  courses?: Course[];
  fetch: () => Promise<void>;
  addCourseToSemester: (semesterId: string, course: Course) => void;
  removeCourseFromSemester: (semesterId: string, courseId: string) => void;
  addSemesters: (semesterNumber: number) => void;
  removeSemesters: () => void;
  saveSemesters: () => Promise<void>;
  isInUse: (course: Course) => boolean;
}

export const useSemesterStore = create<SemesterStore>((set, get) => ({
  fetch: async () => {
    const session = getSession();
    const coursesPromise = ControllerService.recommendCourse(session!.key);
    const semesterPromise = ControllerService.getCoursePlan(session!.key);
    const [courses, semesters] = await Promise.all([
      coursesPromise,
      semesterPromise,
    ]);
    set({
      courses: [...courses],
      semesters: [...(semesters ?? [])],
    });
  },
  addCourseToSemester: (semesterId, course) =>
    set((state) => ({
      semesters: state.semesters!.map((semester) =>
        semester.id === semesterId
          ? { ...semester, courses: [...semester!.courses!, course] }
          : semester
      ),
    })),
  removeCourseFromSemester: (semesterId, courseId) =>
    set((state) => ({
      semesters: state!.semesters!.map((semester) =>
        semester.id === semesterId
          ? {
              ...semester,
              courses: semester!.courses!.filter(
                (course) => course.id !== courseId
              ),
            }
          : semester
      ),
    })),
  saveSemesters: async () => {
    const session = getSession();
    const state = get();

    await ControllerService.setCoursePlan(session!.key, state.semesters!);
  },
  isInUse: (course) => {
    const state = get();
    const isInUse = state!
      .semesters!.map((semester) => {
        const index = semester!.courses!.find((c) => c.id == course.id);
        return index !== undefined;
      })
      .reduce((prev, curr) => prev || curr, false);
    return isInUse;
  },
  addSemesters: (semesterNumber: number) => {
    const id = uuidv4();
    const newSemester: CoursePlan = {
      id,
      semesterNumber: semesterNumber,
      courses: [],
    };
    set((state) => {
      return {
        semesters: [...state.semesters!, newSemester],
      };
    });
  },
  removeSemesters: () => {
    set((state) => {
      const newSemesters = state.semesters!.slice(
        0,
        state.semesters!.length - 1
      );
      return {
        semesters: [...newSemesters],
      };
    });
  },
}));

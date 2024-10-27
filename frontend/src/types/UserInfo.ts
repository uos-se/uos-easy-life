import { Course } from "./Course";

export interface UserInfo {
    name: string;
    studentId: string;
    major: string;
    courses: Course[]
  }
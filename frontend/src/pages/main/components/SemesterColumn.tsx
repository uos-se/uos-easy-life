import { Course } from "@/api/models/Course";
import { useSemesterStore } from "@/store/semesterStore";
import { dropTargetForElements } from "@atlaskit/pragmatic-drag-and-drop/element/adapter";
import { useEffect, useRef } from "react";
import { SchedulerCard } from "./SchedulerCard";

interface SemesterColumnProps {
  semesterId: string;
  semesterNumber: number;
  courses: Course[];
}

export function SemesterColumn({
  semesterId,
  semesterNumber,
  courses,
}: SemesterColumnProps) {
  const columnRef = useRef<HTMLDivElement>(null);
  const { removeCourseFromSemester, addCourseToSemester } = useSemesterStore();

  const calculateTotalCredits = () => {
    return courses.reduce(
      (total, course) => total + (course.lectureCredit || 0),
      0
    );
  };

  const calculateDesignCredits = () => {
    return courses.reduce(
      (total, course) => total + (course.designCredit || 0),
      0
    );
  };

  const calculateMajorCredits = () => {
    return courses.reduce(
      (total, course) =>
        total +
        (course.majorElective! || course.majorEssential!
          ? course.lectureCredit!
          : 0),
      0
    );
  };

  useEffect(() => {
    const element = columnRef.current;
    if (!element) return;

    const cleanup = dropTargetForElements({
      element,
      onDrag: () => {},
      onDrop: (args) => {
        const { course, sourceSemesterId } = args.source.data as {
          course: Course;
          sourceSemesterId: string;
        };
        if (args.source.data.type === "course") {
          removeCourseFromSemester(sourceSemesterId, course.id!);
          addCourseToSemester(semesterId, course);
        }
      },
    });

    return () => cleanup();
  }, [semesterId, removeCourseFromSemester, addCourseToSemester]);

  return (
    <div className="min-w-[350px] max-w-[350px] h-full border-r last:border-r-0 px-4 flex flex-col">
      <h3 className="font-semibold text-center pb-2 border-b">
        {Math.floor(semesterNumber / 2)} - {(semesterNumber % 2) + 1}학기
        <div className="flex flex-wrap-center gap-2 justify-center">
          <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-green-100 text-green-800">
            <span className="mr-1 text-green-500">학점:</span>{" "}
            {calculateTotalCredits()} 학점
          </span>
          <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-purple-100 text-purple-800">
            <span className="mr-1 text-purple-500">설계학점:</span>{" "}
            {calculateDesignCredits()} 학점
          </span>
          <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-yellow-100 text-yellow-800">
            <span className="mr-1 text-yellow-500">전공학점:</span>
            {calculateMajorCredits()} 학점
          </span>
        </div>
      </h3>
      <div ref={columnRef} className="p-2 flex-1">
        {courses.map((course, index) => (
          <SchedulerCard
            key={course.id}
            course={course}
            index={index}
            semesterId={semesterId}
            isInCourseList={false}
          />
        ))}
      </div>
    </div>
  );
}

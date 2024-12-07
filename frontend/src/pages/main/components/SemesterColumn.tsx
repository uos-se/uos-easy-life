import { Course } from "@/api/models/Course";
import { useSemesterStore } from "@/store/semesterStore";
import { dropTargetForElements } from "@atlaskit/pragmatic-drag-and-drop/element/adapter";
import { useEffect, useRef } from "react";
import { SchedulerCard } from "./SchedulerCard";

interface SemesterColumnProps {
  semesterId: string;
  courses: Course[];
}

export function SemesterColumn({ semesterId, courses }: SemesterColumnProps) {
  const columnRef = useRef<HTMLDivElement>(null);
  const { removeCourseFromSemester, addCourseToSemester } = useSemesterStore();

  const calculateTotalCredits = () => {
    return courses.reduce(
      (total, course) => total + (course.lectureCredit || 0),
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
    <div className="flex-1 border-r last:border-r-0 px-4">
      <h3 className="font-semibold text-center pb-2 border-b">
        Semester {semesterId}
        <span className="block text-sm text-gray-500">
          {calculateTotalCredits()} credits
        </span>
      </h3>
      <div ref={columnRef} className="min-h-[400px] p-2">
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

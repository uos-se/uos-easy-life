import { Course } from "@/api/models/Course";
import { useSemesterStore } from "@/store/semesterStore";
import { dropTargetForElements } from "@atlaskit/pragmatic-drag-and-drop/element/adapter";
import { useEffect, useRef } from "react";
import { SchedulerCard } from "./SchedulerCard";
import { SemesterColumn } from "./SemesterColumn";

export function Scheduler() {
  const { semesters, courses, removeCourseFromSemester } = useSemesterStore();
  const courseListRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const element = courseListRef.current;
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
        }
      },
    });

    return () => cleanup();
  }, [removeCourseFromSemester]);

  return (
    <div className="bg-white p-6 h-[500px] rounded-lg shadow-lg flex mt-4">
      <div className="w-60 border-r pr-4 overflow-scroll" ref={courseListRef}>
        <h3 className="font-semibold text-center pb-2 border-b">
          Available Courses
        </h3>
        {courses.map((course, index) => (
          <SchedulerCard
            key={course.id}
            course={course}
            index={index}
            isInCourseList={true}
          />
        ))}
      </div>
      <div className="flex-1 flex">
        {semesters.map((semester) => (
          <SemesterColumn
            key={semester.id}
            semesterId={semester.id}
            courses={semester.courses}
          />
        ))}
      </div>
    </div>
  );
}

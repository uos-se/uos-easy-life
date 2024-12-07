import { Course } from "@/api/models/Course";
import { useSemesterStore } from "@/store/semesterStore";
import { draggable } from "@atlaskit/pragmatic-drag-and-drop/element/adapter";
import { useEffect, useRef } from "react";

interface SchedulerCardProps {
  course: Course;
  index: number;
  semesterId?: string;
  isInCourseList: boolean;
}

export function SchedulerCard({
  course,
  index,
  semesterId,
  isInCourseList,
}: SchedulerCardProps) {
  const cardRef = useRef<HTMLDivElement>(null);
  const { isInUse, semesters } = useSemesterStore();

  useEffect(() => {
    const element = cardRef.current;
    if (!element) return;

    console.log(course);
    console.log(isInUse(course));

    const cleanup = draggable({
      element,
      getInitialData: () => ({
        type: "course",
        course,
        sourceIndex: index,
        sourceSemesterId: semesterId,
      }),
      canDrag: () => (isInCourseList && !isInUse(course)) || !isInCourseList,
    });

    return () => cleanup();
  }, [course, index, semesterId, semesters]);

  return (
    <div
      ref={cardRef}
      className={`p-3 mb-2 rounded border cursor-pointer ${
        isInCourseList && isInUse(course)
          ? "bg-gray-200 border-gray-300 opacity-50"
          : "bg-gray-50 border-gray-200"
      }`}
    >
      <div className="font-medium text-sm">{course.lectureName}</div>
      <div className="text-xs text-gray-500">
        {course.lectureCredit} credits
      </div>
    </div>
  );
}

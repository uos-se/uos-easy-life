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
      className={`p-3 mb-2 rounded border cursor-pointer w-full ${
        isInCourseList && isInUse(course)
          ? "bg-gray-200 border-gray-300 opacity-50"
          : "bg-gray-50 border-gray-200"
      }`}
    >
      <div className="font-medium text-sm">{course.lectureName}</div>
      <div className="text-xs flex flex-wrap mt-1 gap-2">
        <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-green-100 text-green-800">
          <span className="mr-1 text-green-500">학점:</span>{" "}
          {course.lectureCredit}
        </span>
        {course.designCredit! > 0 && (
          <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-purple-100 text-purple-800">
            <span className="mr-1 text-purple-500">설계학점:</span>{" "}
            {course.designCredit}
          </span>
        )}
        {course.majorEssential && (
          <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-red-100 text-red-800">
            <span className="mr-1 text-red-500">전공 필수</span>
          </span>
        )}
        {course.majorElective && (
          <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-yellow-100 text-yellow-800">
            <span className="mr-1 text-yellow-500">전공 선택</span>
          </span>
        )}
        {course.liberalEssential && (
          <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-lime-100 text-lime-800">
            <span className="mr-1 text-lime-500">교양 필수</span>
          </span>
        )}
        {course.liberalElective && (
          <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-cyan-100 text-cyan-800">
            <span className="mr-1 text-cyan-500">교양 선택</span>
          </span>
        )}
      </div>
    </div>
  );
}

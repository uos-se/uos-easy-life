import { Course } from "@/api/models/Course";
import { useSemesterStore } from "@/store/semesterStore";
import { useUserStore } from "@/store/userStore";
import { dropTargetForElements } from "@atlaskit/pragmatic-drag-and-drop/element/adapter";
import { CheckIcon, MinusIcon } from "@radix-ui/react-icons";
import { useEffect, useRef } from "react";
import { SchedulerCard } from "./SchedulerCard";
import { SemesterColumn } from "./SemesterColumn";

export function Scheduler() {
  const {
    semesters,
    courses,
    removeCourseFromSemester,
    fetch,
    addSemesters,
    removeSemesters,
    saveSemesters,
  } = useSemesterStore();
  const { userInfo } = useUserStore();
  const courseListRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    fetch();

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
  }, []);

  return (
    <div className="bg-white p-6 h-[700px] w-full rounded-lg shadow-lg mt-4">
      <div className="flex h-full">
        <div
          className="w-1/4 border-r pr-4 overflow-scroll"
          ref={courseListRef}
        >
          {courses !== undefined && (
            <>
              {courses.map((course, index) => (
                <SchedulerCard
                  key={course.id}
                  course={course}
                  index={index}
                  isInCourseList={true}
                />
              ))}
            </>
          )}
        </div>
        <div className="flex flex-col w-3/4">
          <div className="flex flex-row-reverse mb-4">
            <button
              onClick={saveSemesters}
              className="bg-green-400 text-white px-4 py-2 rounded-lg hover:bg-green-500 transition duration-200 shadow-md flex items-center mx-1"
            >
              <CheckIcon className="mr-2" />
              저장하기
            </button>
            <button
              onClick={removeSemesters}
              className="text-white px-4 py-2 rounded-lg bg-destructive transition duration-200 shadow-md flex items-center mx-1"
            >
              <MinusIcon className="mr-2" />
              마지막 학기 지우기
            </button>
          </div>
          <div className="flex flex-row h-full w-full overflow-x-scroll px-4 py-0">
            {semesters !== undefined && (
              <>
                {semesters.map((semester) => (
                  <SemesterColumn
                    key={semester.id}
                    semesterId={semester.id!}
                    semesterNumber={semester.semesterNumber!}
                    courses={semester.courses!}
                  />
                ))}
              </>
            )}
            <button
              onClick={() =>
                addSemesters(userInfo!.grade! * 2 + semesters!.length)
              }
              className="bg-muted min-w-[300px] max-w-[300px] text-gray-500 px-4 py-2 rounded hover:bg-muted-foreground transition border-dashed border-2"
            >
              추가하기 +
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

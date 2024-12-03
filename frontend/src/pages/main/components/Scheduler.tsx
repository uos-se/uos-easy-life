import { useSemesterStore } from "@/store/semesterStore";
import {
  DragDropContext,
  Draggable,
  Droppable,
  DropResult,
} from "react-beautiful-dnd";

export function Scheduler() {
  const { semesters, addCourseToSemester, courses } = useSemesterStore();

  const calculateTotalCredits = (semesterId: string) => {
    const semester = semesters.find((sem) => sem.id === semesterId);
    return (
      semester?.courses.reduce(
        (total, course) => total + course.lectureCredit!,
        0
      ) || 0
    );
  };

  const handleDragEnd = (result: DropResult) => {
    if (!result.destination) return;

    const { source, destination } = result;
    console.log(source, destination);

    if (
      source.droppableId === "courseList" &&
      destination.droppableId === "scheduler"
    ) {
      const course = courses[source.index];
      addCourseToSemester(semesters[destination.index].id, course);
    }
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-lg flex">
      <DragDropContext onDragEnd={handleDragEnd}>
        <Droppable droppableId="courseList">
          {(provided) => (
            <div
              ref={provided.innerRef}
              {...provided.droppableProps}
              className="w-1/3 border-r pr-4"
            >
              <h3 className="font-semibold text-center pb-2 border-b">
                교양 (12점 남음)
              </h3>
              <div className="p-3 mb-2 bg-gray-50 rounded border border-gray-200 cursor-pointer">
                "공학도의 창업과 경영 (12 credits)"
              </div>
              <h3 className="font-semibold text-center pb-2 border-b">
                전공 필수(3점 남음)
              </h3>
              {courses.map((course, index) => (
                <Draggable
                  key={course.lectureCode!}
                  draggableId={course.id!}
                  index={index}
                >
                  {(provided) => (
                    <div
                      ref={provided.innerRef}
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                      className="p-3 mb-2 bg-gray-50 rounded border border-gray-200 cursor-pointer"
                    >
                      {course.lectureName} ({course.lectureCredit} credits)
                    </div>
                  )}
                </Draggable>
              ))}
              {provided.placeholder}
            </div>
          )}
        </Droppable>

        <div className="flex-1 grid grid-cols-1 gap-4">
          <div className="flex">
            {semesters.map((semester, index) => (
              <div
                key={semester.id}
                className="flex-1 border-r last:border-r-0 px-4"
              >
                <h3 className="font-semibold text-center pb-2 border-b">
                  Semester {semester.id}
                  <span className="block text-sm text-gray-500">
                    {calculateTotalCredits(semester.id)} credits
                  </span>
                </h3>
              </div>
            ))}
          </div>

          <div className="flex min-h-[400px]">
            {semesters.map((semester, index) => (
              <Droppable
                droppableId={`scheduler-${semester.id}`}
                key={semester.id}
              >
                {(provided) => (
                  <div
                    ref={provided.innerRef}
                    {...provided.droppableProps}
                    className="flex-1 border-r last:border-r-0 px-2"
                  >
                    {semester.courses.map((course) => (
                      <div
                        key={course.id}
                        className="p-3 mb-2 bg-gray-50 rounded border border-gray-200 hover:shadow-sm transition-shadow"
                      >
                        <div className="font-medium text-sm">
                          {course.lectureName}
                        </div>
                        <div className="text-xs text-gray-500">
                          {course.lectureCredit} credits
                        </div>
                      </div>
                    ))}
                    {provided.placeholder}
                  </div>
                )}
              </Droppable>
            ))}
          </div>
        </div>
      </DragDropContext>
    </div>
  );
}

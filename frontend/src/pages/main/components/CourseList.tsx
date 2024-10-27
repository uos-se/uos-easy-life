import { Course } from "@/types/Course";

interface CourseListProps {
  courses: Course[];
}

export function CourseList({ courses }: CourseListProps) {
  return (
    <div className="bg-white p-6 rounded-lg shadow-lg md:col-span-2">
      <h2 className="text-xl font-semibold mb-4 text-gray-800">다음학기에 들으면 좋을 과목</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {courses.map((course) => (
          <div key={course.id} className="bg-white p-4 rounded-md shadow-sm hover:shadow transition-all duration-200 border border-gray-100">
            <h3 className="font-semibold text-base text-indigo-700 mb-2">{course.lectureName}({course.lectureCode})
              </h3>
            <div className="flex flex-wrap gap-2">
              <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-green-100 text-green-800">
                <span className="mr-1 text-green-500">학점:</span> {course.lectureCredit}
              </span>
              <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-purple-100 text-purple-800">
                <span className="mr-1 text-purple-500">공학소양학점:</span> {course.lectureEngineeringCredit}
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

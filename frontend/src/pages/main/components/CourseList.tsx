import { Skeleton } from "@/components/ui/skeleton";
import { useUserStore } from "@/store/userStore";

export function CourseList() {
  const { recommendedCourses } = useUserStore();

  return (
    <div className="bg-white p-6 rounded-lg shadow-lg md:col-span-2">
      <h2 className="text-xl font-semibold mb-4 text-gray-800">
        다음학기에 들으면 좋을 과목
      </h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {recommendedCourses !== undefined ? (
          recommendedCourses.map((course) => (
            <div
              key={course.id}
              className="bg-white p-4 rounded-md shadow-sm border border-gray-100 cursor-pointer">
              <h3 className="font-semibold text-base text-indigo-700 mb-2">
                {course.lectureName}({course.lectureCode})
              </h3>
              <div className="flex flex-wrap gap-2">
                <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-green-100 text-green-800">
                  <span className="mr-1 text-green-500">학점:</span>{" "}
                  {course.lectureCredit}
                </span>
                <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-purple-100 text-purple-800">
                  <span className="mr-1 text-purple-500">공학소양학점:</span>{" "}
                  {course.lectureCredit}
                </span>
                {(course.majorElective || course.majorEssential) && (
                  <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-yellow-100 text-yellow-800">
                    <span className="mr-1 text-yellow-500">전공</span>
                  </span>
                )}
                {course.majorEssential && (
                  <span className="inline-flex items-center px-2 py-1 rounded text-xs font-medium bg-red-100 text-red-800">
                    <span className="mr-1 text-red-500">전공 필수 과목</span>
                  </span>
                )}
              </div>
            </div>
          ))
        ) : (
          <>
            <Skeleton>
              <div className="rounded-md w-auto h-[120px]"></div>
            </Skeleton>
            <Skeleton>
              <div className="rounded-md w-auto h-[120px]"></div>
            </Skeleton>
            <Skeleton>
              <div className="rounded-md w-auto h-[120px]"></div>
            </Skeleton>
            <Skeleton>
              <div className="rounded-md w-auto h-[120px]"></div>
            </Skeleton>
          </>
        )}
      </div>
    </div>
  );
}

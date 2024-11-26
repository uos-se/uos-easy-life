import { Skeleton } from "@/components/ui/skeleton";
import { useCourseStore } from "@/store/courseStore";
import { useUserAcademicStatusStore } from "@/store/userAcademicStatusStore";

export function AcademicProgress() {
  const { fetchRecommendedCourses } = useCourseStore();
  const {
    totalRequiredCredit,
    totalCompletedCredit,
    majorRequiredCredit,
    majorCompletedCredit,
    majorEssentialRequiredCredit,
    majorEssentialCompletedCredit,
    liberalRequiredCredit,
    liberalCompletedCredit,
    liberalEssentialRequiredCredit,
    liberalEssentialCompletedCredit,
    engineeringRequiredCredit,
    engineeringCompletedCredit,
    generalRequiredCredit,
    generalCompletedCredit,
    minimumTotalGradePointAverage,
    totalGradePointAverage,
  } = useUserAcademicStatusStore();

  const calc = (complete: number, required: number) => {
    return Math.min((complete / required) * 100, 100);
  };

  const barData = [
    {
      name: "전체",
      value: calc(totalCompletedCredit!, totalRequiredCredit!),
      completed: totalCompletedCredit,
      required: totalRequiredCredit,
    },
    {
      name: "전공",
      value: calc(majorCompletedCredit!, majorRequiredCredit!),
      completed: majorCompletedCredit,
      required: majorRequiredCredit,
    },
    {
      name: "전공 필수",
      value: calc(
        majorEssentialCompletedCredit!,
        majorEssentialRequiredCredit!
      ),
      completed: majorEssentialCompletedCredit,
      required: majorEssentialRequiredCredit,
    },
    {
      name: "교양",
      value: calc(liberalCompletedCredit!, liberalRequiredCredit!),
      completed: liberalCompletedCredit,
      required: liberalRequiredCredit,
    },
    {
      name: "교양 필수",
      value: calc(
        liberalEssentialCompletedCredit!,
        liberalEssentialRequiredCredit!
      ),
      completed: liberalEssentialCompletedCredit,
      required: liberalEssentialRequiredCredit,
    },
    {
      name: "공학 소양",
      value: calc(engineeringCompletedCredit!, engineeringRequiredCredit!),
      completed: engineeringCompletedCredit,
      required: engineeringRequiredCredit,
    },
    {
      name: "일반",
      value: calc(generalCompletedCredit!, generalRequiredCredit!),
      completed: generalCompletedCredit,
      required: generalRequiredCredit,
    },
  ];

  return totalCompletedCredit !== undefined ? (
    <div className="bg-white p-6 rounded-lg shadow-lg">
      <h2 className="text-xl font-semibold mb-4 text-gray-800">
        내 학점 한 눈에 보기
      </h2>
      <div className="flex flex-col">
        {barData.map((item, index) => (
          <div
            key={index}
            className="flex items-center mb-2 text-sm hover:bg-gray-100 p-0.5 hover:cursor-pointer transition duration-200"
            onClick={() => {
              console.log(index);
              // fetchRecommendedCourses()
            }}
          >
            <div className="w-1/4 text-right pr-2">{item.name}</div>
            <div className="relative w-7/12 bg-gray-200 rounded">
              <div
                className="top-0 left-0 h-6 bg-muted-foreground rounded"
                style={{ width: `${item.value}%` }}
              ></div>
            </div>
            <div className="ml-2">
              <strong>{item.completed}</strong>/{item.required}
            </div>
          </div>
        ))}
      </div>
      <div className="mt-2">
        <span
          className={`p-2.5 rounded-lg shadow-md text-sm ${
            totalGradePointAverage! < minimumTotalGradePointAverage!
              ? "bg-red-100"
              : "bg-blue-100"
          }`}
        >
          평균 평점: <strong>{totalGradePointAverage}</strong>
        </span>
        <span className="ml-2 text-xs text-muted-foreground">
          {totalGradePointAverage! < minimumTotalGradePointAverage!
            ? "최소 평점 보다 낮아요. 평점 관리에 유의하세요!"
            : "최소 평점 보다 높아요!"}
        </span>
      </div>
    </div>
  ) : (
    <div className="h-[350px] p-6 rounded-lg shadow-lg bg-white flex justify-center items-center">
      <Skeleton className="w-[300px] h-[300px] rounded-lg" />
    </div>
  );
}

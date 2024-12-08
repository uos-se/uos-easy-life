import { Skeleton } from "@/components/ui/skeleton";
import { useUserStore } from "@/store/userStore";

export function AcademicProgress() {
  const { academicStatus } = useUserStore();

  if (!academicStatus) return;

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
  } = academicStatus;

  const calc = (complete: number, required: number) => {
    return Math.min((complete / required) * 100, 100);
  };

  const barData = [
    {
      name: "전체",
      value: calc(totalCompletedCredit!, totalRequiredCredit!),
      completed: totalCompletedCredit,
      required: totalRequiredCredit,
      color: "bg-green-100",
      boldColor: "bg-green-500",
    },
    {
      name: "전공",
      value: calc(majorCompletedCredit!, majorRequiredCredit!),
      completed: majorCompletedCredit,
      required: majorRequiredCredit,
      color: "bg-yellow-100",
      boldColor: "bg-yellow-500",
    },
    {
      name: "전공 필수",
      value: calc(
        majorEssentialCompletedCredit!,
        majorEssentialRequiredCredit!
      ),
      completed: majorEssentialCompletedCredit,
      required: majorEssentialRequiredCredit,
      color: "bg-red-100",
      boldColor: "bg-red-500",
    },
    {
      name: "교양",
      value: calc(liberalCompletedCredit!, liberalRequiredCredit!),
      completed: liberalCompletedCredit,
      required: liberalRequiredCredit,
      color: "bg-cyan-100",
      boldColor: "bg-cyan-500",
    },
    {
      name: "교양 필수",
      value: calc(
        liberalEssentialCompletedCredit!,
        liberalEssentialRequiredCredit!
      ),
      completed: liberalEssentialCompletedCredit,
      required: liberalEssentialRequiredCredit,
      color: "bg-lime-100",
      boldColor: "bg-lime-500",
    },
    {
      name: "공학 소양",
      value: calc(engineeringCompletedCredit!, engineeringRequiredCredit!),
      completed: engineeringCompletedCredit,
      required: engineeringRequiredCredit,
      color: "bg-amber-100",
      boldColor: "bg-amber-500",
    },
    {
      name: "일반",
      value: calc(generalCompletedCredit!, generalRequiredCredit!),
      completed: generalCompletedCredit,
      required: generalRequiredCredit,
      color: "bg-gray-100",
      boldColor: "bg-gray-500",
    },
  ];

  return totalCompletedCredit !== undefined ? (
    <div className="bg-white p-6 rounded-lg shadow-lg h-[450px]">
      <h2 className="text-xl font-semibold mb-4 text-gray-800">
        내 학점 한 눈에 보기
      </h2>
      <div className="flex flex-col">
        {barData.map((item, index) => (
          <div
            key={index}
            className="flex items-center mb-2 text-sm p-0.5 py-1"
          >
            <div className="w-1/4 text-right pr-2">{item.name}</div>
            <div className={`relative w-7/12 ${item.color} rounded`}>
              <div
                className={`top-0 left-0 h-6 rounded ${item.boldColor}`}
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
    <div className="h-[450px] p-6 rounded-lg shadow-lg bg-white flex justify-center items-center">
      <Skeleton className="w-[300px] h-[300px] rounded-lg" />
    </div>
  );
}

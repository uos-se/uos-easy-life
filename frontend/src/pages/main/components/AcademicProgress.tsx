import { useCourseStore } from "@/store/courseStore";
import ReactApexChart from "react-apexcharts";

export function AcademicProgress() {
  const { filterCourses } = useCourseStore();

  const handleRadialBarClick = () => {
    const criteria = "criteria"; // some criteria
    filterCourses(criteria);
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-lg">
      <ReactApexChart
        options={{
          chart: {
            height: 350,
            type: "radialBar",
            events: {
              click: handleRadialBarClick, // Add click event
            },
          },
          plotOptions: {
            radialBar: {
              dataLabels: {
                name: {
                  fontSize: "16px",
                  color: "#667eea",
                },
                value: {
                  fontSize: "20px",
                  fontWeight: 600,
                  color: "#4c51bf",
                },
                total: {
                  show: true,
                  label: "민혁아 졸업하자",
                  fontSize: "16px",
                  color: "#667eea",
                  formatter: function (w) {
                    return (
                      w.globals.seriesTotals.reduce(
                        (a: number, b: number) => a + b,
                        0
                      ) + "%"
                    );
                  },
                },
              },
            },
          },
          colors: ["#667eea", "#9f7aea", "#ed64a6", "#48bb78", "#4299e1"],
          labels: [
            "Overall",
            "Major Required",
            "Major Elective",
            "General Ed",
            "Electives",
          ],
        }}
        series={[75, 80, 60, 90, 70]}
        type="radialBar"
        height={350}
      />
    </div>
  );
}

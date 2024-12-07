import { Header } from "@/components/layout/Header";
import { useUserStore } from "@/store/userStore";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AcademicProgress } from "./components/AcademicProgress";
import { CourseList } from "./components/CourseList";
import { Scheduler } from "./components/Scheduler";

function formatTimestamp(timestamp: number | null | undefined): string {
  if (!timestamp) return "Never";
  const date = new Date(timestamp);
  return date.toLocaleString();
}

export function Main() {
  const { session, userInfo, load, sync } = useUserStore();
  const nav = useNavigate();
  const [isSync, setIsSync] = useState<boolean>(false);
  const { name, studentId, major } = userInfo || {};

  const onHandleSync = async () => {
    if (!session) return;
    setIsSync(true);
    await sync();
    setIsSync(false);
  };

  useEffect(() => {
    if (!session) {
      nav("/login");
      return;
    }
    load();
  }, [session, load, nav]);

  if (!session) return null;

  return (
    <div className="min-h-screen bg-gradient-to-br from-zinc-50 to-indigo-50">
      <Header />
      <main className="max-w-7xl mx-auto px-4 py-8">
        <section className="bg-white rounded-lg shadow-lg p-6 mb-6">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-2xl font-bold text-gray-800">
                {name || "Loading..."}
              </h1>
              <p className="text-gray-600">
                {studentId || "Student ID"} | {major || "Major"}
              </p>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-sm text-gray-500 cursor-text">
                Last Updated: {formatTimestamp(userInfo?.updatedAt)}
              </span>
              <button
                onClick={onHandleSync}
                className="flex items-center space-x-2 bg-indigo-500 text-white px-4 py-2 rounded-md hover:bg-indigo-600 transition-colors duration-200"
                disabled={isSync}
              >
                <span>{isSync ? "Syncing..." : "Sync"}</span>
                {isSync && (
                  <svg className="w-5 h-5 animate-spin" viewBox="0 0 24 24">
                    <path
                      fill="currentColor"
                      d="M12 4V2A10 10 0 0 0 2 12h2a8 8 0 0 1 8-8zm0 16v2a10 10 0 0 0 10-10h-2a8 8 0 0 1-8 8z"
                    />
                  </svg>
                )}
              </button>
            </div>
          </div>
        </section>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <AcademicProgress />
          <CourseList />
        </div>
        <Scheduler />
      </main>
    </div>
  );
}

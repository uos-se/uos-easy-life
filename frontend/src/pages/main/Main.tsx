import { Header } from "@/components/layout/Header";
import { UserInfo } from "@/types/UserInfo";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AcademicProgress } from "./components/AcademicProgress";
import { CourseList } from "./components/CourseList";
import { useSessionStore } from "@/store/sessionStore";
import { ControllerService } from "@/services/ControllerService";
import { UserFullInfo } from "@/models/UserFullInfo";

const demoUser: UserInfo = {
  name: "홍길동",
  studentId: "2019123456",
  major: "컴퓨터공학과",
};

const fetchUserInfo = async (session: string): Promise<UserInfo> => {
  const userInfo: UserFullInfo = await ControllerService.getUserFullInfo(
    session
  );
  return {
    name: userInfo.name || demoUser.name,
    studentId: userInfo.studentId || demoUser.studentId,
    major: userInfo.major || demoUser.major,
  };
};

export function Main() {
  const { session } = useSessionStore();

  const [userInfo, setUserInfo] = useState<UserInfo>(demoUser);

  const [isSync, setIsSync] = useState<boolean>(false);
  const nav = useNavigate();

  const onSync = async () => {
    if (!session) return;
    try {
      setIsSync(true);
      const { key, id, password } = session;

      await ControllerService.syncUser(key, id, password);

      const userInfo = await fetchUserInfo(key);
      setUserInfo(userInfo);
    } catch (err) {
      console.error("Error during user sync:", err); // 에러 로깅
      alert("User sync failed. Please try again."); // 사용자 피드백
    } finally {
      setIsSync(false);
    }
    // setCourses(courses);
  };

  useEffect(() => {
    // if (!session) {
    //   nav("/login");
    //   return;
    // }

    //todo: 위에 주석 풀면서 if(session)도 삭제
    if (session) {
      (async () => {
        const userInfo = await fetchUserInfo(session.key);
        setUserInfo(userInfo);
      })();
    }
  }, [session, nav]);

  return (
    <div className="min-h-screen bg-gradient-to-br from-zinc-50 to-indigo-50">
      <Header />
      <main className="max-w-7xl mx-auto px-4 py-8">
        <section className="bg-white rounded-lg shadow-lg p-6 mb-6">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-2xl font-bold text-gray-800">
                {userInfo?.name || "Loading..."}
              </h1>
              <p className="text-gray-600">
                {userInfo?.studentId || "Student ID"} |{" "}
                {userInfo?.major || "Major"}
              </p>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-sm text-gray-500 cursor-text">
                Last Updated: 2024-10-25 13:12:47
              </span>
              <button
                onClick={onSync}
                className="flex items-center space-x-2 bg-indigo-500 text-white px-4 py-2 rounded-md hover:bg-indigo-600 transition-colors duration-200"
                disabled={isSync}
              >
                <span>{isSync ? "Syncing..." : "Sync"}</span>
                <svg
                  className={`w-5 h-5 ${isSync ? "animate-spin" : ""}`}
                  viewBox="0 0 24 24"
                >
                  <path
                    fill="currentColor"
                    d="M12 4V2A10 10 0 0 0 2 12h2a8 8 0 0 1 8-8zm0 16v2a10 10 0 0 0 10-10h-2a8 8 0 0 1-8 8z"
                  />
                </svg>
              </button>
            </div>
          </div>
        </section>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <AcademicProgress />
          <CourseList />
        </div>
      </main>
    </div>
  );
}

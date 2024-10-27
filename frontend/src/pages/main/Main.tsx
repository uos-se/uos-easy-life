import { useSessionContext } from "@/context/useSessionContext";
import { ReloadIcon } from "@radix-ui/react-icons";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

interface Course {
  id: string;
  lectureName: string;
  lectureCode: string;
  lectureCredit: number;
  lectureGrade: number;
}

interface UserInfo {
  name: string;
  studentId: string;
  major: string;
  courses: Course[];
}

const fetchUserInfo = async (session: string): Promise<UserInfo> => {
  const res = await fetch(`/api/user/full?session=${session}`);
  const userInfo = await res.json();
  return userInfo as UserInfo;
};

export function Main() {
  const { session, logout } = useSessionContext();
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [isSync, setIsSync] = useState<boolean>(false);
  const nav = useNavigate();

  const onSync = async () => {
    setIsSync(true);
    await new Promise(resolve => setTimeout(resolve, 1000));
    setIsSync(false);
    if (!session) return;
    const { key, id, password } = session;
    await fetch(
      `/api/user/sync?session=${key}&portalId=${id}&portalPassword=${password}`
    );
    const userInfo = await fetchUserInfo(key);
    setUserInfo(userInfo);
  };

  // useEffect(() => {
  //   if (!session) {
  //     nav("/login");
  //     return;
  //   }

  //   (async () => {
  //     const userInfo = await fetchUserInfo(session.key);
  //     setUserInfo(userInfo);
  //   })();
  // }, [session, nav]);

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-white p-4 rounded-tr-lg rounded-tl-lg border-b shadow-sm">
          <div className="flex justify-end items-center space-x-4">
            <button 
              onClick={() => logout()}
              className="text-foreground hover:text-primary text-sm"
            >
              Logout
            </button>
          </div>
        </header>
      <main className="w-[1000px] mx-auto px-4 py-4">
        <section className="w-full text-lg text-muted-foreground">
          <div className="w-full flex flex-rows items-center justify-between">
            <p className="">
              <span>{userInfo?.name || '정민혁'}</span>
              {" | "}
              <span>{userInfo?.studentId || '2019920048'}</span>
            </p>
            <div className=" flex flex-rows items-center">
              <span className="mr-2">{"Last Updated: 2024-10-25 13:12:47"}</span>
              {isSync ?
                <ReloadIcon
                  className="animate-spin text-black"
                />
                : <ReloadIcon
                className="hover:cursor-pointer text-black"
                onClick={onSync}
                />}
            </div>
          </div>
        </section>
        <section className="grid grid-cols-3 gap-4">
        <div className="bg-white p-6 rounded-lg shadow-sm">
          <h2 className="text-xl font-semibold mb-4">Status</h2>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-sm col-span-2">
          <h2 className="text-xl font-semibold mb-4">Courses</h2>
          {userInfo ? (
            <ul className="space-y-4">
              {userInfo.courses.map((course) => (
                <li key={course.id} className="bg-gray-50 p-4 rounded-lg">
                  <h3 className="font-bold">{course.lectureName}</h3>
                  <p className="text-gray-600">Code: {course.lectureCode}</p>
                  <p className="text-gray-600">Credits: {course.lectureCredit}</p>
                  <p className="text-gray-600">Grade: {course.lectureGrade}</p>
                </li>
              ))}
            </ul>
          ) : (
            <div className="text-center text-gray-500">Loading courses...</div>
          )}
        </div>
        </section>
      </main>
    </div>
  );
}

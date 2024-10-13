import { useEffect, useState } from "react";
import { useSessionContext } from "../../context/useSessionContext";
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
  const nav = useNavigate();

  const onSync = async () => {
    if (!session) return;
    const { key, id, password } = session;
    await fetch(
      `/api/user/sync?session=${key}&portalId=${id}&portalPassword=${password}`
    );
    const userInfo = await fetchUserInfo(key);
    setUserInfo(userInfo);
  };

  useEffect(() => {
    // Check if the session is valid. If not, redirect to the login page.
    if (!session) {
      nav("/login");
      return;
    }

    // Fetch user info and set it to the state.
    (async () => {
      const userInfo = await fetchUserInfo(session.key);
      setUserInfo(userInfo);
    })();
  }, [session, nav]);

  return (
    <div>
      Main
      <div>
        <button onClick={onSync}>Sync</button>
        <button onClick={() => logout()}>Logout</button>
        {userInfo ? (
          <div>
            <div>Name: {userInfo.name}</div>
            <div>Student ID: {userInfo.studentId}</div>
            <div>Major: {userInfo.major}</div>
            <div>
              Courses:
              <ul>
                {userInfo.courses.map((course) => (
                  <li key={course.id}>
                    {course.lectureName} ({course.lectureCode}) -{" "}
                    {course.lectureCredit} credits, grade: {course.lectureGrade}
                  </li>
                ))}
              </ul>
            </div>
          </div>
        ) : (
          <div>User info is not loaded</div>
        )}
      </div>
    </div>
  );
}

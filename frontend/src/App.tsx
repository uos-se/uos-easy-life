import { useState } from "react";

export function App() {
  const [userInfo, setUserInfo] = useState<string>("");
  const [id, setId] = useState<string>("");
  const [pw, setPw] = useState<string>("");

  const onLogin = async () => {
    const session = await fetch(
      `/api/login?portalId=${id}&portalPassword=${pw}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    ).then((res) => res.text());

    const userInfo = await fetch(`/api/user?session=${session}`).then((res) =>
      res.json()
    );
    setUserInfo(JSON.stringify(userInfo));
  };

  return (
    <div>
      <h1>Uos Easy Life</h1>
      {userInfo === "" ? (
        <>
          <input
            type="text"
            placeholder="ID"
            value={id}
            onChange={(e) => setId(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={pw}
            onChange={(e) => setPw(e.target.value)}
          />
          <button onClick={onLogin}>Login</button>
        </>
      ) : (
        <div>{userInfo}</div>
      )}
    </div>
  );
}

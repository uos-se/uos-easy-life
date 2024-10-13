import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSessionContext } from "../../context/useSessionContext";

export function Login() {
  const { session, login } = useSessionContext();
  const [id, setId] = useState<string>("");
  const [password, setPw] = useState<string>("");
  const nav = useNavigate();

  // Check if the session is valid. If valid, redirect to the main page.
  useEffect(() => {
    if (session) nav("/");
  }, [session, nav]);

  const onLogin = () => login(id, password);

  return (
    <div>
      <input
        type="text"
        placeholder="ID"
        value={id}
        onChange={(e) => setId(e.target.value)}
        id="id"
        autoComplete="username"
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPw(e.target.value)}
        id="pw"
        autoComplete="current-password"
      />
      <button onClick={onLogin}>Login</button>
    </div>
  );
}

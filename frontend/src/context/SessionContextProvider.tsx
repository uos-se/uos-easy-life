import { useCallback, useEffect, useState } from "react";
import { Session, SessionContext } from "./SessionContext";

const LOCAL_STORAGE_KEY = "uos-easy-life-session";

const validateSessionKey = async (session: string) => {
  const res = await fetch(`/api/user?session=${session}`);
  return res.status === 200;
};

const createSession = async (id: string, password: string) => {
  const res = await fetch(
    `/api/login?portalId=${id}&portalPassword=${password}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  return await res.text();
};

export function SessionContextProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [session, setSession] = useState<Session | null>(null);

  const logout = useCallback(() => {
    setSession(null);
    localStorage.removeItem(LOCAL_STORAGE_KEY);
  }, []);

  const login = useCallback(
    async (id: string, password: string) => {
      const sessionKey = await createSession(id, password);
      if (!sessionKey) logout();
      else setSession({ key: sessionKey, id, password });
      localStorage.setItem(
        LOCAL_STORAGE_KEY,
        JSON.stringify({ key: sessionKey, id, password })
      );
    },
    [logout]
  );

  useEffect(() => {
    // Check if session is stored in the local storage.
    const sessionString = localStorage.getItem(LOCAL_STORAGE_KEY);

    // If session is not stored in the local storage, do nothing.
    if (!sessionString) return;

    (async () => {
      // If session is not stored in the local storage, do nothing.
      const session = JSON.parse(sessionString) as Session;
      if (!session) return;

      // If the session key is valid, set the session.
      if (await validateSessionKey(session.key)) {
        setSession(session);
        return;
      }

      // If the session exists but the key is invalid, try to create a new session.
      await login(session.id, session.password);
    })();
  }, [login]);

  return (
    <SessionContext.Provider
      value={{
        session: session,
        login,
        logout,
      }}>
      {children}
    </SessionContext.Provider>
  );
}

import { useSessionStore } from "@/store/sessionStore";
import { ReactNode, useEffect } from "react";

export function SessionProvider({ children }: { children: ReactNode }) {
  const { login, validateSessionKey } = useSessionStore();

  useEffect(() => {
    const sessionString = localStorage.getItem("uos-easy-life-session");
    if (!sessionString) return;

    (async () => {
      const storedSession = JSON.parse(sessionString);
      if (await validateSessionKey(storedSession.key)) {
        useSessionStore.setState({ session: storedSession });
      } else {
        await login(storedSession.id, storedSession.password);
      }
    })();
  }, [login, validateSessionKey]);

  return <>{children}</>;
}

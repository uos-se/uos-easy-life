import { Session } from "@/context/SessionContext";
import { create } from "zustand";

interface SessionStore {
  session: Session | null;
  login: (id: string, password: string) => void;
  logout: () => void;
  validateSessionKey: (session: Session) => Promise<boolean>;
}

const LOCAL_STORAGE_KEY = "uos-easy-life-session";

export const useSessionStore = create<SessionStore>((set) => ({
  session: null,

  login: async (id, password) => {
    const res = await fetch(
      `/api/login?portalId=${id}&portalPassword=${password}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    const sessionKey = await res.text();
    if (res.status !== 200 || !sessionKey) {
      set({ session: null });
      localStorage.removeItem(LOCAL_STORAGE_KEY);
      return false;
    }

    const newSession = { key: sessionKey, id, password };
    set({ session: newSession });
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(newSession));
    return true;
  },

  logout: () => {
    set({ session: null });
    localStorage.removeItem(LOCAL_STORAGE_KEY);
  },

  validateSessionKey: async (session) => {
    const res = await fetch(`/api/user?session=${session}`);
    const text = await res.text();
    return res.status === 200 && !!text;
  },
}));

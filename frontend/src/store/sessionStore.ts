import { ControllerService } from "@/api/services/ControllerService";
import { create } from "zustand";

export interface Session {
  key: string;
  id: string;
  password: string;
}

export interface SessionStore {
  session: Session | null;
  login: (id: string, password: string) => Promise<boolean>;
  logout: () => void;
  validateSessionKey: (session: Session) => Promise<boolean>;
}

const LOCAL_STORAGE_KEY = "uos-easy-life-session";

export const useSessionStore = create<SessionStore>((set) => ({
  session: null,

  login: async (id, password) => {
    try {
      const sessionKey = await ControllerService.login(id, password);

      // 세션 키가 없거나 응답이 비정상적인 경우
      if (!sessionKey) {
        set({ session: null });
        localStorage.removeItem(LOCAL_STORAGE_KEY);
        return false;
      }

      // 세션 저장
      const newSession: Session = { key: sessionKey, id, password };
      set({ session: newSession });
      localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(newSession));
      return true;
    } catch (error) {
      console.error("Login failed:", error);
      set({ session: null });
      localStorage.removeItem(LOCAL_STORAGE_KEY);
      return false;
    }
  },

  logout: async () => {
    try {
      const currentSession = useSessionStore.getState().session;

      // 세션이 없는 경우 처리하지 않음
      if (!currentSession) return;

      // OpenAPI Generator의 logout 메서드 호출
      await ControllerService.logout(currentSession.key);
    } catch (error) {
      console.error("Logout failed:", error);
    } finally {
      // 상태 초기화
      set({ session: null });
      localStorage.removeItem(LOCAL_STORAGE_KEY);
    }
  },

  validateSessionKey: async (session) => {
    try {
      const isValid = await ControllerService.check(session.key);
      return isValid;
    } catch (error) {
      console.error("Session validation failed:", error);
      return false;
    }
  },
}));

export const getSession = () => useSessionStore.getState().session;

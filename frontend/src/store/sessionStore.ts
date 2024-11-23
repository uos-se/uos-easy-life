import { create } from "zustand";

interface Session {
  key: string;
  id: string;
  password: string;
}

interface SessionStore {
  session: Session | null;
  login: (id: string, password: string) => void;
  logout: () => void;
}

export const useSessionStore = create<SessionStore>((set) => ({
  session: null, // 초기 상태
  login: (id, password) =>
    set({
      session: {
        key: btoa(`${id}:${password}`), // Base64로 세션 키 생성 (예시)
        id,
        password,
      },
    }),
  logout: () => set({ session: null }), // 세션 초기화
}));

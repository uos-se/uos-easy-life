import { Course, UserAcademicStatusDTO, UserFullInfo } from "@/api";
import { ControllerService } from "@/api/services/ControllerService";
import { create } from "zustand";

export type Session = {
  key: string;
  portalId: string;
  portalPassword: string;
};

export interface SessionStore {
  isInitialized: boolean; // "아예 세션 정보가 없는상태"와 "세션이 local storage에 있긴한데 이게 유효한지는아직모르는상태"를 구분하기 위해 사용된다.
  session: Session | null;
  userInfo: UserFullInfo | null;
  academicStatus: UserAcademicStatusDTO | null;
  recommendedCourses: Course[];
  login: (id: string, password: string) => Promise<boolean>;
  logout: () => void;
  initialize: () => Promise<void>;
  load: () => Promise<void>;
  sync: () => Promise<void>;
}

const LOCAL_STORAGE_KEY = "uos-easy-life-session";

export const useUserStore = create<SessionStore>((set) => ({
  isInitialized: false,
  session: null,
  userInfo: null,
  academicStatus: null,
  recommendedCourses: [],

  initialize: async () => {
    try {
      const sessionString = localStorage.getItem(LOCAL_STORAGE_KEY);

      // 만약 세션 정보가 로컬 스토리지에 저장되지 않았다면 단순히 logout된 상태로 간주
      if (!sessionString) throw new Error();

      // 만약 세션 정보가 로컬 스토리지에 저장되어 있다면, 세션 정보가 유효한지 검증
      const session = JSON.parse(sessionString) as Session;
      const isValid = await ControllerService.check(session.key);

      // 세션 정보가 유효하지 않은 경우 로그아웃 처리
      if (!isValid) throw new Error();

      // 세션 정보가 존재하고 유효한 경우 세션 정보 저장
      set({
        isInitialized: true,
        session,
      });
    } catch {
      // 세션 정보가 없거나 유효하지 않은 경우 로그아웃 처리
      set({ isInitialized: true });
      useUserStore.getState().logout();
    }
  },

  login: async (id, password) => {
    try {
      const sessionKey = await ControllerService.login({
        portalId: id,
        portalPassword: password,
      });

      // 세션 키가 없거나 응답이 비정상적인 경우 로그아웃 처리
      if (!sessionKey) throw new Error();

      // 세션 저장
      const newSession: Session = {
        key: sessionKey,
        portalId: id,
        portalPassword: password,
      };

      set({ session: newSession });
      localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(newSession));
      return true;
    } catch (error) {
      console.error("Login failed:", error);
      useUserStore.getState().logout();
      return false;
    }
  },

  logout: async () => {
    try {
      // 상태 삭제
      const currentSession = useUserStore.getState().session;
      set({
        session: null,
        userInfo: null,
        academicStatus: null,
      });

      // 세션이 없는 경우 처리하지 않음
      if (!currentSession) return;

      // Logout 요청
      await ControllerService.logout(currentSession.key);
    } catch (error) {
      console.error("Logout failed:", error);
    } finally {
      localStorage.removeItem(LOCAL_STORAGE_KEY);
    }
  },

  load: async () => {
    const session = useUserStore.getState().session;
    if (!session) return;

    const userInfoPromise = ControllerService.getUserFullInfo(session.key);
    const academicStatusPromise = ControllerService.getUserAcademicStatus(
      session.key
    );
    const recommendedCoursesPromise = ControllerService.recommendCourse(
      session.key
    );

    const [userInfo, academicStatus, recommendedCourses] = await Promise.all([
      userInfoPromise,
      academicStatusPromise,
      recommendedCoursesPromise,
    ]);

    set({
      userInfo,
      academicStatus,
      recommendedCourses,
    });
  },

  sync: async () => {
    const session = useUserStore.getState().session;
    if (!session) return;

    try {
      await ControllerService.syncUser(
        session.key,
        session.portalId,
        session.portalPassword
      );

      await useUserStore.getState().load();
    } catch (error) {
      console.error("Sync failed:", error);
    }
  },
}));

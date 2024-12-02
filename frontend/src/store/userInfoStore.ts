import { UserFullInfo } from "@/api/models/UserFullInfo";
import { ControllerService } from "@/api/services/ControllerService";
import { create } from "zustand";
import { getSession } from "./sessionStore";

export interface UserInfoStore extends UserFullInfo {
  fetchAllUserStatus: () => void;
}

export const useUserInfoStore = create<UserInfoStore>((set) => ({
  fetchAllUserStatus: async () => {
    const session = getSession()!;
    const user = await ControllerService.getUserFullInfo(session.key);
    set({
      ...user,
    });
  },
}));

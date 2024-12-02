import { UserAcademicStatusDTO } from "@/api/models/UserAcademicStatusDTO";
import { ControllerService } from "@/api/services/ControllerService";
import { create } from "zustand";
import { getSession } from "./sessionStore";

export interface UserAcademicStatusStore extends UserAcademicStatusDTO {
  fetchAcademicStatus: () => Promise<void>;
}

export const useUserAcademicStatusStore = create<UserAcademicStatusStore>(
  (set) => ({
    fetchAcademicStatus: async () => {
      const session = getSession()!;
      const academicStatus = await ControllerService.getUserAcademicStatus(
        session.key
      );
      set({
        ...academicStatus,
      });
    },
  })
);

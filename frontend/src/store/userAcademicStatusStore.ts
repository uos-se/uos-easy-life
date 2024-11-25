import { UserAcademicStatusDTO } from "@/models/UserAcademicStatusDTO";
import { MockControllerService } from "@/services/MockControllerService";
import { create } from "zustand";

export interface UserAcademicStatusStore extends UserAcademicStatusDTO {
  fetchAcademicStatus: () => Promise<void>;
}

export const useUserAcademicStatusStore = create<UserAcademicStatusStore>(
  (set) => ({
    fetchAcademicStatus: async () => {
      const academicStatus = await MockControllerService.getUserAcademicStatus(
        ""
        // session.key
      );
      console.log("works");
      console.log(academicStatus);
      set({
        ...academicStatus,
      });
    },
  })
);

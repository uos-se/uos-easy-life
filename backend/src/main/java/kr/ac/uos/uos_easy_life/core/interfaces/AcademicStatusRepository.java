package kr.ac.uos.uos_easy_life.core.interfaces;

import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;

public interface AcademicStatusRepository {
  public void setAcademicStatus(String userId, UserAcademicStatus academicStatus);

  public UserAcademicStatus getAcademicStatus(String userId);
}

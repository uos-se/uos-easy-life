package kr.ac.uos.uos_easy_life.core.interfaces;

import java.util.List;

import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;

public interface UosApi {
  // 세션을 통해 유저의 기본 정보를 반환 (이를 통해 학번을 획득)
  public UserBasicInfo getUserInfo(UosSession session);

  // 현재 유저가 수강했고 수강 중인 모든 과목 코드를 반환
  public List<String> getUserCourseCodes(UosSession session, String studentId);

  // 현재 유저의 학적 정보를 반환
  public UserAcademicStatus getUserAcademicStatus(UosSession session, String name, String studentId);

  // 유저가 어학성적을 취득했는지 여부를 반환
  public boolean isLanguageCertificationCompleted(UosSession session, String name, String studentId);

  // 유저가 봉사활동을 완료했는지 여부를 반환
  public boolean isVolunteerCompleted(UosSession session, String name, String studentId);
}

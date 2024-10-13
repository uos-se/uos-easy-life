package kr.ac.uos.uos_easy_life.core.interfaces;

import java.util.List;

import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;

public interface UosApi {
  public UserBasicInfo getUserInfo(UosSession session);

  public List<String> getUserCourseCodes(UosSession session, String studentId);
}

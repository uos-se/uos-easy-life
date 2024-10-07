package kr.ac.uos.uos_easy_life.core.interfaces;

import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.PortalUserBasicInfo;
import java.util.List;

public interface UosPortalApi {
  public PortalUserBasicInfo getUserInfo(String portalSession, String userId);

  public List<Course> getCourseList(String portalSession);
}

package kr.ac.uos.uos_easy_life.core.interfaces;

import java.util.List;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;
import kr.ac.uos.uos_easy_life.core.model.UosSession;

public interface UosApi {
  public UserBasicInfo getUserInfo(UosSession session);

  public List<Course> getUserCourseList(UosSession session);
}

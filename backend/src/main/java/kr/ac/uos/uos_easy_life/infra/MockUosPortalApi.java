package kr.ac.uos.uos_easy_life.infra;

import java.util.List;
import org.springframework.stereotype.Component;
import kr.ac.uos.uos_easy_life.core.interfaces.UosPortalApi;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.PortalUserBasicInfo;

@Component
public class MockUosPortalApi implements UosPortalApi {

  @Override
  public PortalUserBasicInfo getUserInfo(String portalSession, String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUserInfo'");
  }

  @Override
  public List<Course> getCourseList(String portalSession) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCourseList'");
  }


}

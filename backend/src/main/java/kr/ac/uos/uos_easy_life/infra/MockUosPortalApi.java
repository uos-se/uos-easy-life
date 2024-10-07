package kr.ac.uos.uos_easy_life.infra;

import java.util.List;
import org.springframework.stereotype.Component;
import kr.ac.uos.uos_easy_life.core.interfaces.UosPortalApi;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.Department;
import kr.ac.uos.uos_easy_life.core.model.PortalUserBasicInfo;

@Component
public class MockUosPortalApi implements UosPortalApi {

  @Override
  public PortalUserBasicInfo getUserInfo(String portalSession, String userId) {
    return new PortalUserBasicInfo("홍길동", "2018000000", 2, 1, List.of(Department.ComputerScience));
  }

  @Override
  public List<Course> getCourseList(String portalSession) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCourseList'");
  }
}

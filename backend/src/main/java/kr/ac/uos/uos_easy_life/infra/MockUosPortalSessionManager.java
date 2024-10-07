package kr.ac.uos.uos_easy_life.infra;

import org.springframework.stereotype.Component;
import kr.ac.uos.uos_easy_life.core.interfaces.UosPortalSessionManager;

@Component
public class MockUosPortalSessionManager implements UosPortalSessionManager {

  @Override
  public String createPortalSession(String portalId, String portalPassword) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createPortalSession'");
  }

  @Override
  public boolean isSessionValid(String session) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isSessionValid'");
  }

}

package kr.ac.uos.uos_easy_life.core.interfaces;

import kr.ac.uos.uos_easy_life.core.model.UosSession;

public interface UosSessionManager {
  public UosSession createUosSession(String portalId, String portalPassword);

  public boolean isSessionValid(UosSession session);
}

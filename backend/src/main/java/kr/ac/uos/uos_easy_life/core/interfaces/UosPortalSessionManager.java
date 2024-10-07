package kr.ac.uos.uos_easy_life.core.interfaces;

public interface UosPortalSessionManager {
  public String createPortalSession(String portalId, String portalPassword);

  public boolean isSessionValid(String session);
}

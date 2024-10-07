package kr.ac.uos.uos_easy_life.core.model;

public class UosSession {
  private final String portalSession; // JSESSIONID
  private final String wiseSession; // UOSSESSION
  private final String uclassSession; // PHPSESSID

  public UosSession(String portalSession, String wiseSession, String uclassSession) {
    this.portalSession = portalSession;
    this.wiseSession = wiseSession;
    this.uclassSession = uclassSession;
  }

  public String getPortalSession() {
    return portalSession;
  }

  public String getWiseSession() {
    return wiseSession;
  }

  public String getUclassSession() {
    return uclassSession;
  }

  @Override
  public String toString() {
    return "UosSession{" + //
        "portalSession='" + portalSession + '\'' + //
        ", wiseSession='" + wiseSession + '\'' + //
        ", uclassSession='" + uclassSession + '\'' + //
        '}';
  }
}

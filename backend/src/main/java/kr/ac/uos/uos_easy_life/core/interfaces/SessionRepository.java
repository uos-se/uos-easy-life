package kr.ac.uos.uos_easy_life.core.interfaces;

public interface SessionRepository {
  public String createSession(String userId);

  public String getUserIdBySession(String session);

  public void deleteSession(String session);
}


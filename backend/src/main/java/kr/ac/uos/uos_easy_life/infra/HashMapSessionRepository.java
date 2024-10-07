package kr.ac.uos.uos_easy_life.infra;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import kr.ac.uos.uos_easy_life.core.interfaces.SessionRepository;

@Repository
public class HashMapSessionRepository implements SessionRepository {
  private Map<String, String> sessionMap = new HashMap<>();

  @Override
  public String createSession(String userId) {
    String session = UUID.randomUUID().toString();
    sessionMap.put(session, userId);
    return session;
  }

  @Override
  public String getUserIdBySession(String session) {
    return sessionMap.get(session);
  }

  @Override
  public void deleteSession(String session) {
    sessionMap.remove(session);
  }

}

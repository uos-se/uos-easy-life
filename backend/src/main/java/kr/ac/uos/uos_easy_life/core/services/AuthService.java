package kr.ac.uos.uos_easy_life.core.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import kr.ac.uos.uos_easy_life.core.interfaces.SessionRepository;
import kr.ac.uos.uos_easy_life.core.interfaces.UosApi;
import kr.ac.uos.uos_easy_life.core.interfaces.UosSessionManager;
import kr.ac.uos.uos_easy_life.core.interfaces.UserRepository;
import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.User;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final SessionRepository sessionRepository;
  private final UosSessionManager uosPortalSessionManager;
  private final UosApi uosPortalApi;

  public AuthService(UserRepository userRepository, SessionRepository sessionRepository,
      UosSessionManager uosPortalSessionManager, UosApi uosPortalApi) {
    this.userRepository = userRepository;
    this.sessionRepository = sessionRepository;
    this.uosPortalSessionManager = uosPortalSessionManager;
    this.uosPortalApi = uosPortalApi;
  }

  public String login(String portalId, String portalPassword) {
    // Null check
    if (portalId == null || portalPassword == null) {
      throw new IllegalArgumentException("Portal ID or password is null.");
    }

    // 먼저 DB에 사용자가 있는지 확인한다.
    User user = userRepository.findByPortalId(portalId);

    // 만약 사용자가 있는 경우
    if (user != null) {
      // 비밀번호가 맞는다면 세션을 생성하고 세션 키를 반환한다.
      if (user.checkPassword(portalPassword)) {
        String session = sessionRepository.createSession(user.getId());
        return session;
      }
      // 비밀번호가 틀린 경우 예외를 발생시킨다.
      else {
        throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
      }
    }

    // 사용자가 없는 경우 포털 로그인을 시도하여 로그인 정보가 올바른지 확인한다.
    else {
      UosSession uosSession = uosPortalSessionManager.createUosSession(portalId, portalPassword);

      // 만약 로그인 정보가 올바르다면 사용자를 생성하고 세션을 저장한 후 세션 키를 반환한다.
      if (uosSession != null) {
        // 포털에서 사용자 생성에 필요한 정보를 가져온다.
        UserBasicInfo userInfo = uosPortalApi.getUserInfo(uosSession);
        String id = UUID.randomUUID().toString();

        // 새로운 사용자를 생성한다.
        user = new User(id, userInfo.getName(), userInfo.getStudentId(), 1, 1, portalId, "", "");
        user.setPassword(portalPassword);

        // 사용자를 DB에 저장한다.
        userRepository.save(user);

        // 세션을 생성하고 세션 키를 반환한다.
        String session = sessionRepository.createSession(user.getId());
        return session;
      }

      // 로그인 정보가 올바르지 않은 경우 예외를 발생시킨다.
      else {
        throw new IllegalArgumentException("포탈 로그인 정보가 올바르지 않습니다.");
      }
    }
  }

  public void logout(String session) {
    sessionRepository.deleteSession(session);
  }

  public String getUserIdBySession(String session) {
    return sessionRepository.getUserIdBySession(session);
  }
}

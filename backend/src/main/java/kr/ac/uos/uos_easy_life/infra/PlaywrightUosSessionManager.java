package kr.ac.uos.uos_easy_life.infra;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Cookie;

import jakarta.annotation.PostConstruct;
import kr.ac.uos.uos_easy_life.core.interfaces.UosSessionManager;
import kr.ac.uos.uos_easy_life.core.model.UosSession;

@Component
public class PlaywrightUosSessionManager implements UosSessionManager {

  private final Map<String, UosSession> sessionMap = new HashMap<>();

  private String findCookie(Page page, String cookieName, String domain) {
    List<Cookie> cookies = page.context().cookies();
    String cookieValue = null;
    for (Cookie cookie : cookies) {
      if (cookie.name.equals(cookieName) && (domain == null || cookie.domain.equals(domain))) {
        cookieValue = cookie.value;
        break;
      }
    }
    if (cookieValue == null) {
      throw new RuntimeException("Failed to get " + cookieName + " session");
    }
    return cookieValue;
  }

  @Override
  public UosSession createUosSession(String portalId, String portalPassword) {
    // 기존에 해당 portalId로 생성된 세션이 있는지 확인한다.
    if (sessionMap.containsKey(portalId)) {
      // 만약 세션이 유효하면 해당 세션을 반환한다.
      UosSession session = sessionMap.get(portalId);
      if (isSessionValid(session)) {
        return session;
      }
    }

    // 세션이 없거나 유효하지 않은 경우 새로운 세션을 생성한다.

    // Playwright 초기화.
    Playwright playwright = Playwright.create();
    LaunchOptions options = new LaunchOptions();
    options.setHeadless(true);
    Browser browser = playwright.chromium().launch(options);

    // 포털 로그인
    Page page = browser.newPage();
    page.navigate("https://portal.uos.ac.kr/index.jsp");
    page.fill("#user_id", portalId);
    page.fill("#user_password", portalPassword);
    page.click("button:has-text('Login')");

    // 포털 세션 획득
    page.waitForURL("https://portal.uos.ac.kr/*");
    String portalSession = findCookie(page, "JSESSIONID", null);

    // 와이즈 세션 획득
    page.navigate("https://wise.uos.ac.kr");
    page.waitForURL("https://wise.uos.ac.kr/*");
    String wiseSession = findCookie(page, "UOSSESSION", "wise.uos.ac.kr");

    // 유클래스 세션 획득
    page.navigate("https://uclass.uos.ac.kr/exsignon/sso/sso_index.php");
    page.waitForURL("https://uclass.uos.ac.kr/*");
    String uclassSession = findCookie(page, "PHPSESSID", null);

    // Playwright 종료
    browser.close();
    playwright.close();

    // 세션을 생성한다.
    UosSession uosSession = new UosSession(portalSession, wiseSession, uclassSession);

    // 세션을 저장하고 반환한다.
    sessionMap.put(portalId, uosSession);
    return uosSession;
  }

  @Override
  public boolean isSessionValid(UosSession session) {
    if (session == null) {
      return false;
    }

    try {
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://portal.uos.ac.kr/uos/SessionCheck.eps"))
          .GET().header("Cookie", "JSESSIONID=" + session).build();
      HttpClient client = HttpClient.newHttpClient();
      HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() != 200) {
        return false;
      }
      String body = (String) response.body();
      return body.contains("true");
    } catch (Exception e) {
      return false;
    }
  }

  @PostConstruct
  public void init() {
    /**
     * Playwright는 맨 처음 실행될 때 Chrome 등의 의존성을 다운로드 받는다.
     * 이 작업은 시간이 오래 걸리므로 서버가 시작될 때 미리 Playwright를 초기화해둔다.
     * `@PostConstruct` 어노테이션을 사용하면 스프링 빈이 생성될 때 해당 메소드가 실행된다.
     */
    Playwright playwright = Playwright.create();
    LaunchOptions options = new LaunchOptions();
    options.setHeadless(true);
    Browser browser = playwright.chromium().launch(options);
    browser.close();
    playwright.close();
  }
}

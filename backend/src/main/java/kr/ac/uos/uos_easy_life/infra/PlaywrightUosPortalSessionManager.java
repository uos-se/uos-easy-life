package kr.ac.uos.uos_easy_life.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Cookie;
import kr.ac.uos.uos_easy_life.core.interfaces.UosPortalSessionManager;

@Component
public class PlaywrightUosPortalSessionManager implements UosPortalSessionManager {

  private final Map<String, String> sessionMap = new HashMap<>();

  @Override
  public String createPortalSession(String portalId, String portalPassword) {
    // 기존에 해당 portalId로 생성된 세션이 있는지 확인한다.
    if (sessionMap.containsKey(portalId)) {
      // 만약 세션이 유효하면 해당 세션을 반환한다.
      String session = sessionMap.get(portalId);
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
    page.waitForURL("https://portal.uos.ac.kr/*");

    // 세션 쿠키 획득
    List<Cookie> cookies = page.context().cookies();
    String session = null;
    for (Cookie cookie : cookies) {
      if (cookie.name.equals("JSESSIONID")) {
        session = cookie.value;
        break;
      }
    }

    // Playwright 종료
    browser.close();
    playwright.close();

    // 만약 세션을 획득하지 못했다면 예외를 발생시킨다.
    if (session == null) {
      throw new RuntimeException("Failed to get session");
    }

    // 세션을 저장하고 반환한다.
    sessionMap.put(portalId, session);
    return session;
  }

  @Override
  public boolean isSessionValid(String session) {
    if (session == null) {
      return false;
    }

    WebClient client = WebClient.create("https://portal.uos.ac.kr");
    String response = client.get() //
        .uri("/uos/SessionCheck.eps") //
        .header("Cookie", "JSESSIONID=" + session) //
        .retrieve() //
        .bodyToMono(String.class) //
        .block();

    return response.contains("true");
  }
}

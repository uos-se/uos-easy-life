package kr.ac.uos.uos_easy_life.infra;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import org.springframework.stereotype.Component;
import kr.ac.uos.uos_easy_life.core.interfaces.UosApi;
import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;
import org.json.JSONObject;


@Component
public class UosApiImpl implements UosApi {

  @Override
  public UserBasicInfo getUserInfo(UosSession session) {
    String body = "default.locale=CCMN101.KOR";

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request =
          HttpRequest.newBuilder().uri(URI.create("https://wise.uos.ac.kr/Main/onLoad.do"))
              .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
              .header("Cookie", "UOSSESSION=" + session.getWiseSession())
              .POST(HttpRequest.BodyPublishers.ofString(body)).build();
      String response = client.send(request, BodyHandlers.ofString()).body();

      // 응답 파싱
      JSONObject obj = new JSONObject(response);
      JSONObject userInfo = obj.getJSONObject("dmUserInfo");
      String name = userInfo.getString("USER_NM");
      String studentId = userInfo.getString("USER_ID");

      return new UserBasicInfo(name, studentId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<Course> getUserCourseList(UosSession portalSession) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCourseList'");
  }
}

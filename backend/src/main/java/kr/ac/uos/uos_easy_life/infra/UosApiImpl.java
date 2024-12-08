package kr.ac.uos.uos_easy_life.infra;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.json.JSONException;
import org.springframework.stereotype.Component;

import kr.ac.uos.uos_easy_life.core.interfaces.UosApi;
import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;
import kr.ac.uos.uos_easy_life.core.model.UserInfo;

@Component
public class UosApiImpl implements UosApi {

  private final UosApiParser parser;

  private static final String springSemesterCode = "CCMN031.10";
  private static final String summerSemesterCode = "CCMN031.11";
  private static final String fallSemesterCode = "CCMN031.20";
  private static final String winterSemesterCode = "CCMN031.21";

  public UosApiImpl(UosApiParser parser) {
    this.parser = parser;
  }

  private static String getNextSemesterCode(int month) {
    if (3 <= month && month <= 6) {
      return summerSemesterCode;
    } else if (7 <= month && month <= 8) {
      return fallSemesterCode;
    } else if (9 <= month && month <= 12) {
      return winterSemesterCode;
    } else if (1 <= month && month <= 2) {
      return springSemesterCode;
    } else {
      throw new IllegalArgumentException("Invalid month");
    }
  }

  private static String wiseRequest(String path, String body, UosSession session)
      throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://wise.uos.ac.kr" + path))
        .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
        .header("Cookie", "UOSSESSION=" + session.getWiseSession())
        .POST(HttpRequest.BodyPublishers.ofString(body)).build();
    return client.send(request, BodyHandlers.ofString()).body();
  }

  @Override
  public UserBasicInfo getUserBasicInfo(UosSession session) {
    String path = "/Main/onLoad.do";
    String body = "default.locale=CCMN101.KOR";

    try {
      String response = wiseRequest(path, body, session);
      return parser.parseUserBasicInfo(response);
    } catch (IOException | InterruptedException | JSONException e) {
      throw new RuntimeException("Failed to get user basic info", e);
    }
  }

  @Override
  public List<String> getUserCourseCodes(UosSession session, String studentId) {
    String path = "/SCH/SusrMasterInq/list.do";
    String body = "_AUTH_MENU_KEY=SusrMasterInq_2"
        + "&_AUTH_PGM_ID=SusrMasterInq"
        + "&__PRVC_PSBLTY_YN=N"
        + "&_AUTH_TASK_AUTHRT_ID=CCMN_SVC"
        + "&default.locale=CCMN101.KOR"
        + "&%40d1%23strStdntNo=" + studentId
        + "&%40d%23=%40d1%23"
        + "&%40d1%23=dmReqKey"
        + "&%40d1%23tp=dm";

    try {
      String response = wiseRequest(path, body, session);
      return parser.parseUserCourseCodes(response);
    } catch (IOException | InterruptedException | JSONException e) {
      throw new RuntimeException("Failed to get user course codes", e);
    }
  }

  @Override
  public UserAcademicStatus getUserAcademicStatus(UosSession session, String name, String studentId) {
    LocalDate currentDate = LocalDate.now();
    String path = "/SCH/SugtPlanCmpSubject/listGrdtnCmpnCrtr.do";
    String body = "_AUTH_MENU_KEY=SugtPlanCmpSubject_5"
        + "&_AUTH_PGM_ID=SugtPlanCmpSubject"
        + "&__PRVC_PSBLTY_YN=N"
        + "&_AUTH_TASK_AUTHRT_ID=CCMN_SVC"
        + "&default.locale=CCMN101.KOR"
        + "&%40d1%23strAcyr=" + currentDate.getYear()
        + "&%40d1%23strSemstrCd=" + getNextSemesterCode(currentDate.getMonthValue())
        + "&%40d1%23strStdntNo=" + studentId
        + "&%40d1%23strStdntNm=" + URLEncoder.encode(name, StandardCharsets.UTF_8)
        + "&%40d1%23strLocale=CCMN101.KOR"
        + "&%40d1%23strPopDiv="
        + "&%40d%23=%40d1%23"
        + "&%40d1%23=dmReqKey"
        + "&%40d1%23tp=dm";

    try {
      String response = wiseRequest(path, body, session);
      return parser.parseUserAcademicStatus(response);
    } catch (IOException | InterruptedException | JSONException e) {
      throw new RuntimeException("Failed to get user academic status", e);
    }
  }

  @Override
  public boolean isLanguageCertificationCompleted(UosSession session, String name, String studentId) {
    try {
      String response = getCertificationResponse(session, name, studentId);
      return parser.parseCertificationCompleted(response, "졸업인증(외국어)");
    } catch (IOException | InterruptedException | JSONException e) {
      throw new RuntimeException("Failed to get language certification status", e);
    }
  }

  @Override
  public boolean isVolunteerCompleted(UosSession session, String name, String studentId) {
    try {
      String response = getCertificationResponse(session, name, studentId);
      return parser.parseCertificationCompleted(response, "사회봉사영역");
    } catch (IOException | InterruptedException | JSONException e) {
      throw new RuntimeException("Failed to get volunteer certification status", e);
    }
  }

  private String getCertificationResponse(UosSession session, String name, String studentId)
      throws IOException, InterruptedException {
    LocalDate currentDate = LocalDate.now();
    String path = "/SCH/SugtPlanCmpSubject/listStdntInfo.do";
    String body = "_AUTH_MENU_KEY=SugtPlanCmpSubject_5"
        + "&_AUTH_PGM_ID=SugtPlanCmpSubject"
        + "&__PRVC_PSBLTY_YN=N"
        + "&_AUTH_TASK_AUTHRT_ID=CCMN_SVC"
        + "&default.locale=CCMN101.KOR"
        + "&%40d1%23strAcyr=" + currentDate.getYear()
        + "&%40d1%23strSemstrCd=" + getNextSemesterCode(currentDate.getMonthValue())
        + "&%40d1%23strStdntNo=" + studentId
        + "&%40d1%23strStdntNm=" + URLEncoder.encode(name, StandardCharsets.UTF_8)
        + "&%40d1%23strLocale=CCMN101.KOR"
        + "&%40d1%23strPopDiv="
        + "&%40d%23=%40d1%23"
        + "&%40d1%23=dmReqKey"
        + "&%40d1%23tp=dm";

    return wiseRequest(path, body, session);
  }

  @Override
  public UserInfo getUserInfo(UosSession session, String studentId) {
    String path = "/SCH/SusrMasterMgt/studentInfo.do";
    String body = "_AUTH_MENU_KEY=SusrMasterInq_2"
        + "&_AUTH_PGM_ID=SusrMasterInq"
        + "&__PRVC_PSBLTY_YN=N"
        + "&_AUTH_TASK_AUTHRT_ID=CCMN_SVC"
        + "&default.locale=CCMN101.KOR"
        + "&%40d1%23strStdntNo=" + studentId
        + "&%40d%23=%40d1%23"
        + "&%40d1%23=dmReqkey"
        + "&%40d1%23tp=dm";

    try {
      String response = wiseRequest(path, body, session);
      return parser.parseUserInfo(response);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Failed to get user info", e);
    }
  }
}

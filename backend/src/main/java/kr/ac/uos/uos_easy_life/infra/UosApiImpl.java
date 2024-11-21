package kr.ac.uos.uos_easy_life.infra;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import kr.ac.uos.uos_easy_life.core.interfaces.UosApi;
import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;

@Component
public class UosApiImpl implements UosApi {
  private static final String springSemesterCode = "CCMN031.10";
  private static final String summerSemesterCode = "CCMN031.11";
  private static final String fallSemesterCode = "CCMN031.20";
  private static final String winterSemesterCode = "CCMN031.21";

  private static String wiseRequest(String path, String body, UosSession session)
      throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://wise.uos.ac.kr" + path))
        .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
        .header("Cookie", "UOSSESSION=" + session.getWiseSession())
        .POST(HttpRequest.BodyPublishers.ofString(body)).build();
    return client.send(request, BodyHandlers.ofString()).body();
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

  @Override
  public UserBasicInfo getUserInfo(UosSession session) {
    String path = "/Main/onLoad.do";
    String body = "default.locale=CCMN101.KOR";

    try {
      String response = wiseRequest(path, body, session);

      // 응답 파싱
      JSONObject obj = new JSONObject(response);
      JSONObject userInfo = obj.getJSONObject("dmUserInfo");
      String name = userInfo.getString("USER_NM");
      String studentId = userInfo.getString("USER_ID");

      return new UserBasicInfo(name, studentId);
    } catch (IOException | InterruptedException | JSONException e) {
      return null;
    }
  }

  @Override
  public List<String> getUserCourseCodes(UosSession session, String studentId) {
    String path = "/SCH/SusrMasterInq/list.do";
    String body = "_AUTH_MENU_KEY=SusrMasterInq_2&_AUTH_PGM_ID=SusrMasterInq&__PRVC_PSBLTY_YN=N&_AUTH_TASK_AUTHRT_ID=CCMN_SVC&default.locale=CCMN101.KOR&%40d1%23strStdntNo="
        + studentId + "&%40d%23=%40d1%23&%40d1%23=dmReqKey&%40d1%23tp=dm";

    try {
      String response = wiseRequest(path, body, session);

      // 응답 파싱
      JSONObject obj = new JSONObject(response);
      JSONArray courseList = obj.getJSONArray("dsTlsnList");
      List<String> courseCodes = new ArrayList<>();
      for (int i = 0; i < courseList.length(); i++) {
        JSONObject course = courseList.getJSONObject(i);
        String code = course.getString("SBJC_NO");
        courseCodes.add(code);
      }

      return courseCodes;
    } catch (IOException | InterruptedException | JSONException e) {
      return null;
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
      return parseUserAcademicStatus(response);
    } catch (IOException | InterruptedException e) {
      return null;
    }
  }

  public UserAcademicStatus parseUserAcademicStatus(String response) {
    try {
      int totalCompletedCredit = 0;
      int majorCompletedCredit = 0;
      int majorEssentialCompletedCredit = 0;
      int liberalCompletedCredit = 0;
      int liberalEssentialCompletedCredit = 0;
      int engineeringCompletedCredit = 0;
      int generalCompletedCredit = 0;
      double totalGradePointAverage = 0.0;

      JSONObject obj = new JSONObject(response);
      JSONArray academicStatus = obj.getJSONArray("dsGrdtnCmpnCrtr");

      /*
       * Each element of academicStatus is like below:
       * {
       * "COURSE_DIVNM": "공학소양", // detailDomain
       * "CMPN_PNT": 4, // completedCredit
       * "GRDTN_CMPN_CRTR_NM": "교양선택", // category
       * ...
       * }
       */
      for (int i = 0; i < academicStatus.length(); i++) {
        JSONObject status = academicStatus.getJSONObject(i);
        String category = status.getString("GRDTN_CMPN_CRTR_NM");
        String detailDomain = status.optString("COURSE_DIVNM", null);
        int completedCredit = status.getInt("CMPN_PNT");

        if (category.equals("졸업이수학점") && detailDomain == null) {
          totalCompletedCredit = completedCredit;
        } else if (category.equals("전공") && detailDomain == null) {
          majorCompletedCredit = completedCredit;
        } else if (category.equals("전공필수") && detailDomain == null) {
          majorEssentialCompletedCredit = completedCredit;
        } else if (category.equals("교양") && detailDomain == null) {
          liberalCompletedCredit = completedCredit;
        } else if (category.equals("교양필수") && detailDomain == null) {
          liberalEssentialCompletedCredit = completedCredit;
        } else if (category.equals("교양선택") && detailDomain != null && detailDomain.equals("공학소양")) {
          engineeringCompletedCredit = completedCredit;
        } else if (category.equals("일반선택") && detailDomain == null) {
          generalCompletedCredit = completedCredit;
        } else if (category.equals("총평점평균") && detailDomain == null) {
          totalGradePointAverage = status.getDouble("CMPN_PNT");
        }
      }

      return new UserAcademicStatus(
          totalCompletedCredit,
          majorCompletedCredit,
          majorEssentialCompletedCredit,
          liberalCompletedCredit,
          liberalEssentialCompletedCredit,
          engineeringCompletedCredit,
          generalCompletedCredit,
          totalGradePointAverage);
    } catch (JSONException e) {
      return null;
    }
  }

  @Override
  public boolean isLanguageCertificationCompleted(UosSession session, String studentId) {

    /**
     * 1. Get data for payload
     * - strSemstrCd
     * - strAcyr
     */
    String path = "/SCH/SugtPlanCmpSubject/onLoad.do";
    String body = "_AUTH_MENU_KEY=SugtPlanCmpSubject_5&_AUTH_PGM_ID=SugtPlanCmpSubject&__PRVC_PSBLTY_YN=N&_AUTH_TASK_AUTHRT_ID=CCMN_SVC&default.locale=CCMN101.KOR";

    String strSemstrCd;
    String strAcyr;

    try {
      String response = wiseRequest(path, body, session);
      JSONObject obj = new JSONObject(response);
      obj = obj.getJSONObject("dmResOnload");

      strSemstrCd = obj.get("strSemstrCd").toString();
      strAcyr = obj.get("strAcyr").toString();
    } catch (IOException | InterruptedException | JSONException e) {
      e.printStackTrace(System.out);
      return false;
    }

    /**
     * 2. Get actual info
     */
    path = "/SCH/SugtPlanCmpSubject/listStdntInfo.do";
    body = "_AUTH_MENU_KEY=SugtPlanCmpSubject_5"
        + "&_AUTH_PGM_ID=SugtPlanCmpSubject"
        + "&__PRVC_PSBLTY_YN=N"
        + "&_AUTH_TASK_AUTHRT_ID=CCMN_SVC"
        + "&default.locale=CCMN101.KOR"
        + "&%40d1%23strAcyr=" + strAcyr
        + "&%40d1%23strSemstrCd=" + strSemstrCd
        + "&%40d1%23strStdntNo=" + studentId
        // + "&%40d1%23strStdntNm=%EA%B9%80%EC%9B%90%EB%B9%88" // student name 필요한가?
        // Nope
        + "&%40d1%23strLocale=CCMN101.KOR"
        + "&%40d1%23strPopDiv="
        + "&%40d%23=%40d1%23"
        + "&%40d1%23=dmReqKey"
        + "&%40d1%23tp=dm";

    boolean result = false;

    try {
      String response = wiseRequest(path, body, session);
      JSONObject obj = new JSONObject(response);
      JSONArray array = obj.getJSONArray("dsGrdtnCertInfo");

      for (int i = 0; i < array.length(); i++) {
        JSONObject element = array.getJSONObject(i);
        String div = element.get("CERT_DIV_CN").toString();

        if (div.equals("졸업인증(외국어)")) {
          String isPassed = element.get("PASS_YN").toString();

          if (isPassed.equals("Y"))
            result = true;
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return result;
    }

    return result;
  }

  @Override
  public boolean isVolunteerCompleted(UosSession session, String studentId) {

    /**
     * 1. Get data for payload
     * - strSemstrCd
     * - strAcyr
     */
    String path = "/SCH/SugtPlanCmpSubject/onLoad.do";
    String body = "_AUTH_MENU_KEY=SugtPlanCmpSubject_5&_AUTH_PGM_ID=SugtPlanCmpSubject&__PRVC_PSBLTY_YN=N&_AUTH_TASK_AUTHRT_ID=CCMN_SVC&default.locale=CCMN101.KOR";

    String strSemstrCd;
    String strAcyr;

    try {
      String response = wiseRequest(path, body, session);
      JSONObject obj = new JSONObject(response);
      obj = obj.getJSONObject("dmResOnload");

      strSemstrCd = obj.get("strSemstrCd").toString();
      strAcyr = obj.get("strAcyr").toString();
    } catch (IOException | InterruptedException | JSONException e) {
      e.printStackTrace(System.out);
      return false;
    }

    /**
     * 2. Get actual info
     */
    path = "/SCH/SugtPlanCmpSubject/listStdntInfo.do";
    body = "_AUTH_MENU_KEY=SugtPlanCmpSubject_5"
        + "&_AUTH_PGM_ID=SugtPlanCmpSubject"
        + "&__PRVC_PSBLTY_YN=N"
        + "&_AUTH_TASK_AUTHRT_ID=CCMN_SVC"
        + "&default.locale=CCMN101.KOR"
        + "&%40d1%23strAcyr=" + strAcyr
        + "&%40d1%23strSemstrCd=" + strSemstrCd
        + "&%40d1%23strStdntNo=" + studentId
        // + "&%40d1%23strStdntNm=%EA%B9%80%EC%9B%90%EB%B9%88" // student name 필요한가?
        + "&%40d1%23strLocale=CCMN101.KOR"
        + "&%40d1%23strPopDiv="
        + "&%40d%23=%40d1%23"
        + "&%40d1%23=dmReqKey"
        + "&%40d1%23tp=dm";

    boolean result = false;

    try {
      String response = wiseRequest(path, body, session);
      JSONObject obj = new JSONObject(response);
      JSONArray array = obj.getJSONArray("dsGrdtnCertInfo");

      for (int i = 0; i < array.length(); i++) {
        JSONObject element = array.getJSONObject(i);
        String div = element.get("CERT_DIV_CN").toString();

        if (div.equals("사회봉사영역")) {
          String isPassed = element.get("PASS_YN").toString();

          if (isPassed.equals("Y"))
            result = true;
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return result;
    }

    return result;
  }
}

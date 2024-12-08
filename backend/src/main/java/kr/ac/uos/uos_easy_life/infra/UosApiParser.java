package kr.ac.uos.uos_easy_life.infra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import kr.ac.uos.uos_easy_life.core.model.Department;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;
import kr.ac.uos.uos_easy_life.core.model.UserInfo;

@Component
public class UosApiParser {

  public UserBasicInfo parseUserBasicInfo(String response) {
    JSONObject obj = new JSONObject(response);
    JSONObject userInfo = obj.getJSONObject("dmUserInfo");
    String name = userInfo.getString("USER_NM");
    String studentId = userInfo.getString("USER_ID");
    return new UserBasicInfo(name, studentId);
  }

  public List<String> parseUserCourseCodes(String response) {
    JSONObject obj = new JSONObject(response);
    JSONArray courseList = obj.getJSONArray("dsTlsnList");
    List<String> courseCodes = new ArrayList<>();
    for (int i = 0; i < courseList.length(); i++) {
      JSONObject course = courseList.getJSONObject(i);
      String code = course.getString("SBJC_NO");
      courseCodes.add(code);
    }
    return courseCodes;
  }

  public UserAcademicStatus parseUserAcademicStatus(String response) {
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
  }

  public boolean parseCertificationCompleted(String response, String certificationName) {
    JSONArray certInfoArray = (new JSONObject(response)).getJSONArray("dsGrdtnCertInfo");

    for (int i = 0; i < certInfoArray.length(); i++) {
      JSONObject element = certInfoArray.getJSONObject(i);
      if (element.getString("CERT_DIV_CN").equals(certificationName)) {
        return element.getString("PASS_YN").equals("Y");
      }
    }
    throw new JSONException("Certification not found: " + certificationName);
  }

  public UserInfo parseUserInfo(String response) {
    JSONObject obj = new JSONObject(response);
    JSONObject studentInfo = obj.getJSONArray("dsStudentInfo").getJSONObject(0);

    String departmentName = studentInfo.getString("SPRVSN_SCSBJT_NM");
    Department department = Department.fromDepartmentName(departmentName);

    // 이 값은 전체 학기 (e.g. 2학년 2학기 = 4)를 나타낸다. 일단 쓰지 않는다.
    // int semester = studentInfo.getInt("PRMOT_SEMSTR_CNT");

    // 만약 현재 시간이 8월 이전이면 1학기, 8월 이후면 2학기
    int month = LocalDate.now().getMonthValue();
    int semester = month < 8 ? 1 : 2;

    int grade = studentInfo.getInt("STDNT_GRADE");

    return new UserInfo(department, grade, semester);
  }

}

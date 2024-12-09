package kr.ac.uos.uos_easy_life.infra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import kr.ac.uos.uos_easy_life.core.model.Department;
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

  public double parseTotalGradePointAverage(String response) {
    double totalGradePointAverage = -1.0;

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
      if (category.equals("총평점평균")) {
        totalGradePointAverage = status.getDouble("CMPN_PNT");
        break;
      }
    }

    if (totalGradePointAverage == -1.0) {
      throw new JSONException("Total grade point average not found");
    }

    return totalGradePointAverage;
  }

  public ExpectedUserAcademicStatus parseExpectedUserAcademicStatus(String response) {
    int totalCompletedCredit = -1;
    int majorCompletedCredit = -1;
    int majorEssentialCompletedCredit = -1;
    int liberalCompletedCredit = -1;
    int liberalEssentialCompletedCredit = -1;
    int engineeringCompletedCredit = -1;
    int generalCompletedCredit = -1;

    JSONObject obj = new JSONObject(response);
    JSONArray academicStatus = obj.getJSONArray("dsEspectCmpnPnt");

    /*
     * Each element of academicStatus is like below:
     * {
     * "MJR_DIVCD": "SUSR003.01",
     * "COURSE_DIVNM": null,
     * "GRDTN_CMPN_CRTR_CD": "SUGT001.01",
     * "COURSE_DIVCD": null,
     * "COMPNO_MJR": "20031",
     * "MUMM_PNT": 130,
     * "COMPNO_MJR_NM": "수학과",
     * "TLSN_CNT": 7,
     * "TLSN_SEASON_PNT": 0,
     * "CMPN_TLSN_PNT": 115,
     * "OTPT_ORDR": "1",
     * "MJR_DIVNM": "주전공",
     * "GRDTN_CMPN_CRTR_NM": "졸업이수학점",
     * "MXMM_PNT": 0,
     * "TLSN_SEASON_CNT": 0,
     * "CMPN_SBJECT_CNT": 36,
     * "CMPN_PNT": 97,
     * "TLSN_PNT": 18,
     * "STDNT_NO": "2020920999"
     * }
     */
    for (int i = 0; i < academicStatus.length(); i++) {
      JSONObject status = academicStatus.getJSONObject(i);
      String category = status.getString("GRDTN_CMPN_CRTR_NM");
      String detailDomain = status.optString("COURSE_DIVNM", null);
      int completedCredit = status.getInt("CMPN_TLSN_PNT");

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
      }
    }

    // validate
    if (totalCompletedCredit == -1 || majorCompletedCredit == -1 || majorEssentialCompletedCredit == -1
        || liberalCompletedCredit == -1 || liberalEssentialCompletedCredit == -1 || engineeringCompletedCredit == -1
        || generalCompletedCredit == -1) {
      throw new JSONException("User expected academic status not found");
    }

    return new ExpectedUserAcademicStatus(
        totalCompletedCredit,
        majorCompletedCredit,
        majorEssentialCompletedCredit,
        liberalCompletedCredit,
        liberalEssentialCompletedCredit,
        engineeringCompletedCredit,
        generalCompletedCredit);
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

  class ExpectedUserAcademicStatus {
    int totalCompletedCredit = -1;
    int majorCompletedCredit = -1;
    int majorEssentialCompletedCredit = -1;
    int liberalCompletedCredit = -1;
    int liberalEssentialCompletedCredit = -1;
    int engineeringCompletedCredit = -1;
    int generalCompletedCredit = -1;

    public ExpectedUserAcademicStatus(int totalCompletedCredit, int majorCompletedCredit,
        int majorEssentialCompletedCredit, int liberalCompletedCredit,
        int liberalEssentialCompletedCredit, int engineeringCompletedCredit, int generalCompletedCredit) {
      this.totalCompletedCredit = totalCompletedCredit;
      this.majorCompletedCredit = majorCompletedCredit;
      this.majorEssentialCompletedCredit = majorEssentialCompletedCredit;
      this.liberalCompletedCredit = liberalCompletedCredit;
      this.liberalEssentialCompletedCredit = liberalEssentialCompletedCredit;
      this.engineeringCompletedCredit = engineeringCompletedCredit;
      this.generalCompletedCredit = generalCompletedCredit;
    }

    public int getTotalCompletedCredit() {
      return totalCompletedCredit;
    }

    public int getMajorCompletedCredit() {
      return majorCompletedCredit;
    }

    public int getMajorEssentialCompletedCredit() {
      return majorEssentialCompletedCredit;
    }

    public int getLiberalCompletedCredit() {
      return liberalCompletedCredit;
    }

    public int getLiberalEssentialCompletedCredit() {
      return liberalEssentialCompletedCredit;
    }

    public int getEngineeringCompletedCredit() {
      return engineeringCompletedCredit;
    }

    public int getGeneralCompletedCredit() {
      return generalCompletedCredit;
    }
  }
}

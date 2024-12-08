package kr.ac.uos.uos_easy_life.infra;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import kr.ac.uos.uos_easy_life.core.model.Department;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;
import kr.ac.uos.uos_easy_life.core.model.UserInfo;

class UosApiParserTest {

  private final UosApiParser parser = new UosApiParser();
  private final String mockDataPath = "src/test/resources/infra/";

  @Test
  void parseUserBasicInfoTest() throws IOException {
    // given
    File file = new File(mockDataPath + "UserBasicInfo.json");
    String response = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    // when
    UserBasicInfo result = parser.parseUserBasicInfo(response);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("홍길동");
    assertThat(result.getStudentId()).isEqualTo("2020920999");
  }

  @Test
  void parseUserCourseCodesTest() throws IOException {
    // given
    File file = new File(mockDataPath + "UserCourseCodes.json");
    String response = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    // when
    List<String> result = parser.parseUserCourseCodes(response);

    // then
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).contains("01108", "71032");
  }

  @Test
  void parseUserAcademicStatusTest() throws IOException {
    // given
    File file = new File(mockDataPath + "UserAcademicStatus.json");
    String response = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    // when
    UserAcademicStatus result = parser.parseUserAcademicStatus(response);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getTotalCompletedCredit()).isEqualTo(97);
    assertThat(result.getMajorCompletedCredit()).isEqualTo(54);
    assertThat(result.getMajorEssentialCompletedCredit()).isEqualTo(18);
    assertThat(result.getLiberalCompletedCredit()).isEqualTo(31);
    assertThat(result.getLiberalEssentialCompletedCredit()).isEqualTo(14);
    assertThat(result.getEngineeringCompletedCredit()).isEqualTo(4);
    assertThat(result.getGeneralCompletedCredit()).isEqualTo(3);
    assertThat(result.getTotalGradePointAverage()).isEqualTo(3.5);
  }

  @Test
  void parseCertificationCompletedTest() throws IOException {
    // given
    File file = new File(mockDataPath + "Certification.json");
    String response = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    // when
    boolean volunteerResult = parser.parseCertificationCompleted(response, "사회봉사영역");
    boolean languageResult = parser.parseCertificationCompleted(response, "졸업인증(외국어)");

    // then
    assertThat(volunteerResult).isFalse();
    assertThat(languageResult).isTrue();
  }

  @Test
  void parseUserInfoTest() throws IOException {
    // given
    File file = new File(mockDataPath + "UserInfo.json");
    String response = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    // when
    UserInfo result = parser.parseUserInfo(response);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getDepartment()).isEqualTo(Department.ComputerScience);
    assertThat(result.getGrade()).isEqualTo(3);
    assertThat(result.getSemester()).isEqualTo(2);
  }
}

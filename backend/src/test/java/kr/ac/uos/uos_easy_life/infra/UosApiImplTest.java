package kr.ac.uos.uos_easy_life.infra;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;

class UosApiImplTest {

  UosApiImpl uosApiImpl = new UosApiImpl();

  @Test
  void parseUserAcademicStatusTest() throws IOException {
    // given
    File file = new File("src/test/resources/infra/UserAcademicStatus.json");
    String response = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    // when
    UserAcademicStatus result = uosApiImpl.parseUserAcademicStatus(response);

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

    File file = new File("src/test/resources/infra/Certification.json");
    String response = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    boolean volunteerResult = uosApiImpl.parseCertificationCompleted(response, "사회봉사영역");
    boolean languageResult = uosApiImpl.parseCertificationCompleted(response, "졸업인증(외국어)");

    assertThat(volunteerResult).isFalse();
    assertThat(languageResult).isTrue();
  }
}

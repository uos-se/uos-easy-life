
package kr.ac.uos.uos_easy_life.infra.mock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.ac.uos.uos_easy_life.core.interfaces.AcademicStatusRepository;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;

//@Repository
public class MockAcademicStatusRepository implements AcademicStatusRepository {

  private final Map<String, UserAcademicStatus> academicStatuses = new HashMap<>();

  @Override
  public void setAcademicStatus(String userId, UserAcademicStatus academicStatus) {
    academicStatuses.put(userId, academicStatus);
  }

  @Override
  public UserAcademicStatus getAcademicStatus(String userId) {
    return academicStatuses.get(userId);
  }
}
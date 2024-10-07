package kr.ac.uos.uos_easy_life.core.model;

public enum Department {
  ComputerScience(92, "컴퓨터과학부"),
  GeneralEducation(0, "교양교육부");

  private final int departmentCode;
  private final String departmentName;

  Department(int departmentCode, String departmentName) {
    // 학과 코드는 0보다 크고 100보다 작아야 한다.
    // 특별히 0번은 교양교육부에 할당한다.

    if (departmentCode < 0 || departmentCode >= 100) {
      throw new IllegalArgumentException("학과 코드는 0보다 크고 100보다 작아야 합니다.");
    }
    this.departmentCode = departmentCode;
    this.departmentName = departmentName;
  }

  public static Department fromDepartmentCode(int departmentCode) {
    for (Department department : Department.values()) {
      if (department.getDepartmentCode() == departmentCode) {
        return department;
      }
    }
    throw new IllegalArgumentException("해당하는 학과 코드가 없습니다.");
  }

  public int getDepartmentCode() {
    return departmentCode;
  }

  public String getDepartmentName() {
    return departmentName;
  }
}

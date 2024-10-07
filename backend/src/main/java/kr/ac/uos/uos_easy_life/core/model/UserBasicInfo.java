package kr.ac.uos.uos_easy_life.core.model;

public class UserBasicInfo {
  private String name;
  private String studentId;

  public UserBasicInfo(String name, String studentId) {
    this.name = name;
    this.studentId = studentId;
  }

  public String getName() {
    return name;
  }

  public String getStudentId() {
    return studentId;
  }

  @Override
  public String toString() {
    return "UserBasicInfo{" + //
        "name='" + name + '\'' + //
        ", studentId='" + studentId + '\'' + //
        '}';
  }
}

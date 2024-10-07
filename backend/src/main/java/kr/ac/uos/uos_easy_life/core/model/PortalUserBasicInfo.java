package kr.ac.uos.uos_easy_life.core.model;

import java.util.List;

public class PortalUserBasicInfo {
  private String name;
  private String studentId;
  private int grade;
  private int semester;
  private List<Department> departments;

  public PortalUserBasicInfo(String name, String studentId, int grade, int semester,
      List<Department> departments) {
    this.name = name;
    this.studentId = studentId;
    this.grade = grade;
    this.semester = semester;
    this.departments = departments;
  }

  public String getName() {
    return name;
  }

  public String getStudentId() {
    return studentId;
  }

  public int getGrade() {
    return grade;
  }

  public int getSemester() {
    return semester;
  }

  public List<Department> getDepartments() {
    return departments;
  }

  @Override
  public String toString() {
    return "PortalUserInfo{" + "name='" + name + '\'' + ", grade=" + grade + ", semester="
        + semester + ", departments=" + departments + '}';
  }
}

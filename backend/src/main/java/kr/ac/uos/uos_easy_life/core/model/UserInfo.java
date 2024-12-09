package kr.ac.uos.uos_easy_life.core.model;

public class UserInfo {
  private Department department;
  private int grade;
  private int semester;

  public UserInfo(Department department, int grade, int semester) {
    this.department = department;
    this.grade = grade;
    this.semester = semester;
  }

  public Department getDepartment() {
    return department;
  }

  public int getGrade() {
    return grade;
  }

  public int getSemester() {
    return semester;
  }
}

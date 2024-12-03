package kr.ac.uos.uos_easy_life.core.model;

import java.util.List;

public class UserFullInfo extends UserBasicInfo {
  private String email;
  private String phone;
  private String major;
  private int grade;
  private String status;
  private List<Course> courses;
  private long updatedAt;

  public UserFullInfo(String name, String studentId, String email, String phone, String major,
      int grade, String status, List<Course> courses, long updatedAt) {
    super(name, studentId);
    this.email = email;
    this.phone = phone;
    this.major = major;
    this.grade = grade;
    this.status = status;
    this.courses = courses;
    this.updatedAt = updatedAt;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getMajor() {
    return major;
  }

  public int getGrade() {
    return grade;
  }

  public String getStatus() {
    return status;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public long getUpdatedAt() {
    return updatedAt;
  }
}

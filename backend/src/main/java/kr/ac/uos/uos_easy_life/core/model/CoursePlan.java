package kr.ac.uos.uos_easy_life.core.model;

import java.util.List;

public class CoursePlan {
  private String id;
  private int semesterNumber;
  private List<Course> courses;

  public CoursePlan(String id, int semesterNumber, List<Course> courses) {
    this.id = id;
    this.semesterNumber = semesterNumber;
    this.courses = courses;
  }

  public String getId() {
    return id;
  }

  public int getSemesterNumber() {
    return semesterNumber;
  }

  public List<Course> getCourses() {
    return courses;
  }
}

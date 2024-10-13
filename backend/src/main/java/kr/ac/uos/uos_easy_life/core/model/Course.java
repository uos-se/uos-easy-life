package kr.ac.uos.uos_easy_life.core.model;

public class Course {
  // TODO: 이 클래스를 구현해야 한다.
  private String id; // 사용자 ID
  private String lectureName; // 강의명
  private String lectureCode; // 강의 코드
  private int lectureCredit; // 학점
  private int lectureGrade; // 학년

  public Course(String id, String lectureName, String lectureCode, int lectureCredit,
      int lectureGrade) {
    this.id = id;
    this.lectureName = lectureName;
    this.lectureCode = lectureCode;
    this.lectureCredit = lectureCredit;
    this.lectureGrade = lectureGrade;
  }

  public String getId() {
    return id;
  }

  public String getLectureName() {
    return lectureName;
  }

  public String getLectureCode() {
    return lectureCode;
  }

  public int getLectureCredit() {
    return lectureCredit;
  }

  public int getLectureGrade() {
    return lectureGrade;
  }

  @Override
  public String toString() {
    return "Lecture{" + "id='" + id + '\'' + ", lectureName='" + lectureName + '\''
        + ", lectureCode=" + lectureCode + ", lectureCredit=" + lectureCredit + ", lectureGrade="
        + lectureGrade + '}';
  }
}

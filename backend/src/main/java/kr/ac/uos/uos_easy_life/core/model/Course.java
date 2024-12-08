package kr.ac.uos.uos_easy_life.core.model;

public class Course {
  private final String id; // 서비스 내 고유 ID
  private final String lectureName; // 강의명
  private final String lectureCode; // 강의 코드
  private final int lectureCredit; // 학점
  private final int lectureGrade; // 학년
  private final Department department; // 학과
  private final boolean isMajorElective; // 전공선택
  private final boolean isMajorEssential; // 전공필수
  private final boolean isLiberalElective; // 교양선택
  private final boolean isLiberalEssential; // 교양필수
  private final boolean isEngineering; // 공학소양
  private final boolean isBasicAcademic; // 학문기초
  private final int designCredit; // 설계학점
  private final int lastOpenYear; // 마지막으로 개설된 연도

  public Course(String id, String lectureName, String lectureCode, int lectureCredit,
      int lectureGrade, Department department, boolean isMajorElective, boolean isMajorEssential,
      boolean isLiberalElective, boolean isLiberalEssential, boolean isEngineering,
      boolean isBasicAcademic, int designCredit, int lastOpenYear) {
    this.id = id;
    this.lectureName = lectureName;
    this.lectureCode = lectureCode;
    this.lectureCredit = lectureCredit;
    this.lectureGrade = lectureGrade;
    this.department = department;
    this.isMajorElective = isMajorElective;
    this.isMajorEssential = isMajorEssential;
    this.isLiberalElective = isLiberalElective;
    this.isLiberalEssential = isLiberalEssential;
    this.isEngineering = isEngineering;
    this.isBasicAcademic = isBasicAcademic;
    this.designCredit = designCredit;
    this.lastOpenYear = lastOpenYear;
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

  public Department getDepartment() {
    return department;
  }

  public boolean isMajorElective() {
    return isMajorElective;
  }

  public boolean isMajorEssential() {
    return isMajorEssential;
  }

  public boolean isLiberalElective() {
    return isLiberalElective;
  }

  public boolean isLiberalEssential() {
    return isLiberalEssential;
  }

  public boolean isEngineering() {
    return isEngineering;
  }

  public boolean isBasicAcademic() {
    return isBasicAcademic;
  }

  public int getDesignCredit() {
    return designCredit;
  }

  public int getLastOpenYear() {
    return lastOpenYear;
  }

  @Override
  public String toString() {
    return "Course{" +
        "id='" + id + '\'' +
        ", lectureName='" + lectureName + '\'' +
        ", lectureCode='" + lectureCode + '\'' +
        ", lectureCredit=" + lectureCredit +
        ", lectureGrade=" + lectureGrade +
        ", department=" + department +
        ", isMajorElective=" + isMajorElective +
        ", isMajorEssential=" + isMajorEssential +
        ", isLiberalElective=" + isLiberalElective +
        ", isLiberalEssential=" + isLiberalEssential +
        ", isEngineering=" + isEngineering +
        ", isBasicAcademic=" + isBasicAcademic +
        ", designCredit=" + designCredit +
        ", lastOpenYear=" + lastOpenYear +
        '}';
  }
}

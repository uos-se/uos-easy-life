package kr.ac.uos.uos_easy_life.core.model;

public class Course {
  private String id; // 서비스 내 고유 ID
  private String lectureName; // 강의명
  private String lectureCode; // 강의 코드
  private int lectureCredit; // 학점
  private int lectureGrade; // 학년

  private boolean isMajorElective; // 전선
  private boolean isMajorEssential; // 전필
  private boolean isLiberalElective; // 교선
  private boolean isLiberalEssential; // 교필
  private boolean isEngineering; // 공학소양
  private boolean isBasicAcademic; // 학문기초
  private int generalCredit; // 일반-credit
  private int designCredit; // 설계-credit

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

  public boolean isMajorElective() {
    return isMajorElective;
  }

  public void setMajorElective(boolean majorElective) {
    isMajorElective = majorElective;
  }

  public boolean isMajorEssential() {
    return isMajorEssential;
  }

  public void setMajorEssential(boolean majorEssential) {
    isMajorEssential = majorEssential;
  }

  public boolean isLiberalElective() {
    return isLiberalElective;
  }

  public void setLiberalElective(boolean liberalElective) {
    isLiberalElective = liberalElective;
  }

  public boolean isLiberalEssential() {
    return isLiberalEssential;
  }

  public void setLiberalEssential(boolean liberalEssential) {
    isLiberalEssential = liberalEssential;
  }

  public boolean isEngineering() {
    return isEngineering;
  }

  public void setEngineering(boolean engineering) {
    isEngineering = engineering;
  }

  public boolean isBasicAcademic() {
    return isBasicAcademic;
  }

  public void setBasicAcademic(boolean basicAcademic) {
    isBasicAcademic = basicAcademic;
  }

  public int getGeneralCredit() {
    return generalCredit;
  }

  public void setGeneralCredit(int generalCredit) {
    this.generalCredit = generalCredit;
  }

  public int getDesignCredit() {
    return designCredit;
  }

  public void setDesignCredit(int designCredit) {
    this.designCredit = designCredit;
  }

  @Override
  public String toString() {
    return "Lecture{" + "id='" + id + '\'' + ", lectureName='" + lectureName + '\''
        + ", lectureCode=" + lectureCode + ", lectureCredit=" + lectureCredit + ", lectureGrade="
        + lectureGrade + '}';
  }
}

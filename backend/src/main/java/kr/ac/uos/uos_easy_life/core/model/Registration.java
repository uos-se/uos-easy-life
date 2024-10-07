package kr.ac.uos.uos_easy_life.core.model;

public class Registration {
  private String id; // 등록 ID
  private String userId; // 사용자 ID
  private String courseId; // 수업 ID

  public Registration(String id, String userId, String courseId) {
    this.id = id;
    this.userId = userId;
    this.courseId = courseId;
  }

  public String getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public String getCourseId() {
    return courseId;
  }

  @Override
  public String toString() {
    return "Registration{" + "id='" + id + '\'' + ", userId='" + userId + '\'' + ", courseId='"
        + courseId + '\'' + '}';
  }
}

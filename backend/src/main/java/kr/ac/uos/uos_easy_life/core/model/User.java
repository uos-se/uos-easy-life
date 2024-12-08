package kr.ac.uos.uos_easy_life.core.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User {
  private String id; // 우리 서비스 내 사용자 고유 ID
  private String name; // 이름
  private String studentId; // 학번
  private Department department;// 학과
  private int currentGrade; // 학년
  private int currentSemester; // 학기

  // 로그인에 필요한 정보
  private String portalId; // 포탈 아이디
  private String hashedPortalPassword; // 해시된 비밀번호
  private String salt; // 솔트

  // 메타데이터
  private long createdAt; // 생성일
  private long updatedAt; // 업데이트일

  private static String hashPassword(String password, String salt) {
    int iteration = 10000;
    int keyLength = 256;
    char[] passwordChars = password.toCharArray();
    byte[] saltBytes = salt.getBytes();
    PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iteration, keyLength);
    try {
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      byte[] key = keyFactory.generateSecret(spec).getEncoded();
      return Base64.getEncoder().encodeToString(key);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
    } finally {
      spec.clearPassword();
    }
  }

  private static String getNewSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }

  public User(String id, String name, String studentId, int currentGrade, int currentSemester,
      String portalId, String hashedPassword, String salt) {

    // ID는 valid한 UUID여야 한다.
    if (UUID.fromString(id) == null) {
      throw new IllegalArgumentException("ID는 valid한 UUID여야 합니다.");
    }
    this.id = id;

    // 이름은 최대 20글자까지만 허용한다.
    if (name.length() > 20) {
      throw new IllegalArgumentException("이름은 최대 20글자까지만 허용됩니다.");
    }
    this.name = name;

    // 학번은 10자리 숫자로만 이루어져야 한다.
    if (!studentId.matches("[0-9]{10}")) {
      throw new IllegalArgumentException("학번은 10자리 숫자로만 이루어져야 합니다. 값: " + studentId);
    }
    this.studentId = studentId;

    // 학과는 학번의 5-6번째 자리에 해당하는 학과 코드로부터 추출한다.
    int departmentsCode = Integer.parseInt(studentId.substring(4, 6));
    Department department = Department.fromDepartmentCode(departmentsCode);
    this.department = department;

    this.setCurrentGrade(currentGrade);
    this.setCurrentSemester(currentSemester);

    this.portalId = portalId;
    this.hashedPortalPassword = hashedPassword;
    this.salt = salt;

    /**
     * 아래와 같이 엔티티 클래스 내부에서 시간 등 사이드 이펙트가 있는 코드를 작성하는 것은 좋지 않다. 그러나 이를 리팩토링하는 것은 어렵지
     * 않으므로 프로젝트 초기에는 이렇게 작성한다. 나중에 프로젝트가 커지면 이를 리팩토링하여 최대한 사이드 이펙트가 없도록 리팩토링해야한다.
     */
    this.createdAt = System.currentTimeMillis();
    this.updatedAt = -1;
  }

  private void updateTimestamp() {
    this.updatedAt = System.currentTimeMillis();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getStudentId() {
    return studentId;
  }

  public Department getDepartment() {
    return department;
  }

  public int getCurrentGrade() {
    return currentGrade;
  }

  public void setCurrentGrade(int currentGrade) {
    if (currentGrade < 1 || currentGrade > 5) {
      throw new IllegalArgumentException("학년은 1~5 사이의 값만 허용됩니다. 값: " + currentGrade);
    }
    this.currentGrade = currentGrade;
    this.updateTimestamp();
  }

  public int getCurrentSemester() {
    return currentSemester;
  }

  public void setCurrentSemester(int currentSemester) {
    if (currentSemester != 1 && currentSemester != 2) {
      throw new IllegalArgumentException("학기는 1, 2만 허용됩니다. 값: " + currentSemester);
    }
    this.currentSemester = currentSemester;
    this.updateTimestamp();
  }

  public String getPortalId() {
    return portalId;
  }

  public String getHashedPortalPassword() {
    return hashedPortalPassword;
  }

  public String getSalt() {
    return salt;
  }

  public String setPassword(String password) {
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null.");
    }
    this.salt = getNewSalt();
    this.hashedPortalPassword = hashPassword(password, salt);
    this.updateTimestamp();
    return hashedPortalPassword;
  }

  public boolean checkPassword(String password) {
    if (password == null) {
      throw new IllegalArgumentException("Password is null.");
    }
    return hashPassword(password, salt).equals(hashedPortalPassword);
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(long updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "User{" + "name='" + name + '\'' + ", studentId='" + studentId + '\'' + ", department="
        + department + ", currentGrade=" + currentGrade + ", currentSemester=" + currentSemester
        + '}';
  }
}

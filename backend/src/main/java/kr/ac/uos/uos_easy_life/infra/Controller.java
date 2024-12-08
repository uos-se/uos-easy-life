package kr.ac.uos.uos_easy_life.infra;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.uos.uos_easy_life.core.model.Course;
import kr.ac.uos.uos_easy_life.core.model.CoursePlan;
import kr.ac.uos.uos_easy_life.core.model.User;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatusDTO;
import kr.ac.uos.uos_easy_life.core.model.UserFullInfo;
import kr.ac.uos.uos_easy_life.core.services.AuthService;
import kr.ac.uos.uos_easy_life.core.services.CoursePlanService;
import kr.ac.uos.uos_easy_life.core.services.UserService;

@RestController
@RequestMapping("/api")
public class Controller {

  private final AuthService authService;
  private final UserService userService;
  private final CoursePlanService coursePlanService;

  public Controller(AuthService authService, UserService userService, CoursePlanService coursePlanService) {
    this.authService = authService;
    this.userService = userService;
    this.coursePlanService = coursePlanService;
  }

  @GetMapping("/")
  public String index() {
    return "UOS Easy Life API";
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginDTO loginDTO) {
    String portalId = loginDTO.getPortalId();
    String portalPassword = loginDTO.getPortalPassword();
    String token = authService.login(portalId, portalPassword);
    // TODO: 이거보다 나은 솔루션 없나
    return "\"" + token + "\"";
  }

  @GetMapping("/logout")
  public void logout(@RequestParam String session) {
    authService.logout(session);
  }

  @GetMapping("/check")
  public boolean check(@RequestParam String session) {
    return authService.getUserIdBySession(session) != null;
  }

  @GetMapping("/user")
  public User getUser(@RequestParam String session) {
    String userId = authService.getUserIdBySession(session);
    return userService.getUser(userId);
  }

  @GetMapping("/user/full")
  public UserFullInfo getUserFullInfo(@RequestParam String session) {
    String userId = authService.getUserIdBySession(session);
    return userService.getUserFullInfo(userId);
  }

  @GetMapping("/user/sync")
  public void syncUser(@RequestParam String session, @RequestParam String portalId,
      @RequestParam String portalPassword) {
    String userId = authService.getUserIdBySession(session);
    userService.syncUser(userId, portalId, portalPassword);
  }

  @GetMapping("/user/academic-status")
  public UserAcademicStatusDTO getUserAcademicStatus(@RequestParam String session) {
    String userId = authService.getUserIdBySession(session);
    return userService.getUserAcademicStatus(userId);
  }

  @GetMapping("/user/recommended-course")
  public List<Course> recommendCourse(@RequestParam String session) {
    String userId = authService.getUserIdBySession(session);
    return userService.getRecommendedCourses(userId);
  }

  @PostMapping("/user/course-plan")
  public void setCoursePlan(@RequestParam String session, @RequestBody List<CoursePlan> coursePlan) {
    String userId = authService.getUserIdBySession(session);
    coursePlanService.setCoursePlan(userId, coursePlan);
  }

  @GetMapping("/user/course-plan")
  public List<CoursePlan> getCoursePlan(@RequestParam String session) {
    String userId = authService.getUserIdBySession(session);
    return coursePlanService.getCoursePlan(userId);
  }
}

class LoginDTO {
  private String portalId;
  private String portalPassword;

  public String getPortalId() {
    return portalId;
  }

  public String getPortalPassword() {
    return portalPassword;
  }

  public LoginDTO(String portalId, String portalPassword) {
    this.portalId = portalId;

    if (portalPassword == null) {
      throw new IllegalArgumentException("Portal password is null.");
    }

    this.portalPassword = portalPassword;

    if (portalId == null) {
      throw new IllegalArgumentException("Portal ID is null.");
    }
  }
}
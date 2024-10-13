package kr.ac.uos.uos_easy_life.infra;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.uos.uos_easy_life.core.model.User;
import kr.ac.uos.uos_easy_life.core.model.UserFullInfo;
import kr.ac.uos.uos_easy_life.core.services.AuthService;
import kr.ac.uos.uos_easy_life.core.services.UserService;

@RestController
@RequestMapping("/api")
public class Controller {

  private final AuthService authService;
  private final UserService userService;

  public Controller(AuthService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
  }

  @GetMapping("/")
  public String index() {
    return "UOS Easy Life API";
  }

  @PostMapping("/login")
  public String login(@RequestParam String portalId, @RequestParam String portalPassword) {
    return authService.login(portalId, portalPassword);
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
}

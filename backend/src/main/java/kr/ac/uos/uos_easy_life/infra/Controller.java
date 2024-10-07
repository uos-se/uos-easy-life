package kr.ac.uos.uos_easy_life.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kr.ac.uos.uos_easy_life.core.services.AuthService;

@RestController
@RequestMapping("/api")
public class Controller {

  private final AuthService authService;

  @Autowired
  public Controller(AuthService authService) {
    this.authService = authService;
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
}

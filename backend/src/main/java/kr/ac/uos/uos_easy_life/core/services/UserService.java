package kr.ac.uos.uos_easy_life.core.services;

import org.springframework.stereotype.Service;
import kr.ac.uos.uos_easy_life.core.interfaces.UserRepository;
import kr.ac.uos.uos_easy_life.core.model.User;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUser(String userId) {
    return userRepository.findById(userId);
  }
}

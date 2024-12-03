package kr.ac.uos.uos_easy_life.infra;

import org.springframework.stereotype.Repository;
import kr.ac.uos.uos_easy_life.core.interfaces.UserRepository;
import kr.ac.uos.uos_easy_life.core.model.User;
import java.util.ArrayList;
import java.util.List;

//@Repository
public class MockUserRepository implements UserRepository {

  private final List<User> users = new ArrayList<>();

  @Override
  public User findById(String id) {
    for (User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }

  @Override
  public User findByPortalId(String portalId) {
    for (User user : users) {
      if (user.getPortalId().equals(portalId)) {
        return user;
      }
    }
    return null;
  }

  @Override
  public void save(User user) {
    // Check if the user already exists
    for (User existingUser : users) {
      if (existingUser.getId().equals(user.getId())) {
        throw new RuntimeException("User already exists");
      }
    }
    users.add(user);
  }

  @Override
  public void update(User user) {
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getId().equals(user.getId())) {
        users.set(i, user);
        return;
      }
    }
    throw new RuntimeException("User not found");
  }

  @Override
  public void deleteById(String id) {
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getId().equals(id)) {
        users.remove(i);
        return;
      }
    }
  }
}

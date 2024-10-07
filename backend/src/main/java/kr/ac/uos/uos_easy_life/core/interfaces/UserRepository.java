package kr.ac.uos.uos_easy_life.core.interfaces;

import kr.ac.uos.uos_easy_life.core.model.User;

public interface UserRepository {
  public User findById(String id);

  public User findByPortalId(String portalId);

  public void save(User user);

  public void update(User user);

  public void deleteById(String id);
}

package kr.ac.uos.uos_easy_life.infra;

import org.springframework.stereotype.Repository;
import kr.ac.uos.uos_easy_life.core.interfaces.UserRepository;
import kr.ac.uos.uos_easy_life.core.model.User;

@Repository
public class MockUserRepository implements UserRepository {

  @Override
  public User findById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public User findByPortalId(String portalId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByPortalId'");
  }

  @Override
  public void save(User user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void update(User user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void deleteById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }

}

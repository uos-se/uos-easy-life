package kr.ac.uos.uos_easy_life.core.interfaces;

import java.util.List;
import kr.ac.uos.uos_easy_life.core.model.Course;

public interface CourseRepository {
  public Course findById(String id);

  public Course findByCode(String code);

  public Course findByName(String name);

  public List<Course> findAll();

  public void save(Course course);

  public void update(Course course);

  public void deleteById(String id);
}

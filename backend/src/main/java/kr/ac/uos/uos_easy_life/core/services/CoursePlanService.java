package kr.ac.uos.uos_easy_life.core.services;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.ac.uos.uos_easy_life.core.interfaces.CoursePlanRepository;
import kr.ac.uos.uos_easy_life.core.model.CoursePlan;

@Service
public class CoursePlanService {
  private final CoursePlanRepository coursePlanRepository;

  public CoursePlanService(CoursePlanRepository coursePlanRepository) {
    this.coursePlanRepository = coursePlanRepository;
  }

  public void setCoursePlan(String userId, List<CoursePlan> coursePlan) {
    coursePlanRepository.setCoursePlan(userId, coursePlan);
  }

  public List<CoursePlan> getCoursePlan(String userId) {
    return coursePlanRepository.getCoursePlan(userId);
  }
}

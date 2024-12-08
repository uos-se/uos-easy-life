package kr.ac.uos.uos_easy_life.core.interfaces;

import java.util.List;

import kr.ac.uos.uos_easy_life.core.model.CoursePlan;

public interface CoursePlanRepository {
    void setCoursePlan(String userId, List<CoursePlan> coursePlans);

    List<CoursePlan> getCoursePlan(String userId);
}

package kr.ac.uos.uos_easy_life.core.model;

public class UserAcademicStatus {
    // 전체이수학점
    private int totalCompletedCredit;
    // 전공이수학점
    private int majorCompletedCredit;
    // 교양이수학점
    private int liberalCompletedCredit;
    // 공학소양이수학점
    private int engineeringCompletedCredit;
    // 일반선택이수학점
    private int generalCompletedCredit;
    // 전체학점평점
    private double totalGradePointAverage;

    public UserAcademicStatus(
            int totalCompletedCredit,
            int majorCompletedCredit,
            int liberalCompletedCredit,
            int engineeringCompletedCredit,
            int generalCompletedCredit,
            double totalGradePointAverage) {
        this.totalCompletedCredit = totalCompletedCredit;
        this.majorCompletedCredit = majorCompletedCredit;
        this.liberalCompletedCredit = liberalCompletedCredit;
        this.engineeringCompletedCredit = engineeringCompletedCredit;
        this.generalCompletedCredit = generalCompletedCredit;
        this.totalGradePointAverage = totalGradePointAverage;
    }

    public int getTotalCompletedCredit() {
        return totalCompletedCredit;
    }

    public int getMajorCompletedCredit() {
        return majorCompletedCredit;
    }

    public int getLiberalCompletedCredit() {
        return liberalCompletedCredit;
    }

    public int getEngineeringCompletedCredit(){
        return engineeringCompletedCredit;
    }

    public int getGeneralCompletedCredit() {
        return generalCompletedCredit;
    }

    public double getTotalGradePointAverage() {
        return totalGradePointAverage;
    }

    @Override
    public String toString() {
        return "UserAcademicStatus{" + //
                "totalCompletedCredit=" + totalCompletedCredit + //
                ", majorCompletedCredit=" + majorCompletedCredit + //
                ", liberalCompletedCredit=" + liberalCompletedCredit + //
                ", engineeringCompletedCredit=" + engineeringCompletedCredit + //
                ", generalCompletedCredit=" + generalCompletedCredit + //
                ", totalGradePointAverage=" + totalGradePointAverage + //
                '}';
    }
}

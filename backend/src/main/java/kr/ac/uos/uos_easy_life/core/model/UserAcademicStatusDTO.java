package kr.ac.uos.uos_easy_life.core.model;

public class UserAcademicStatusDTO {
    // 전체요구학점
    private int totalRequiredCredit;
    // 전체이수학점
    private int totalCompletedCredit;
    // 전공요구학점
    private int majorRequiredCredit;
    // 전공이수학점
    private int majorCompletedCredit;
    // 교양요구학점
    private int liberalRequiredCredit;
    // 교양이수학점
    private int liberalCompletedCredit;
    // 공학소양요구학점
    private int engineeringRequiredCredit;
    // 공학소양이수학점
    private int engineeringCompletedCredit;
    // 일반선택요구학점
    private int generalRequiredCredit;
    // 일반선택이수학점
    private int generalCompletedCredit;
    // 최소전체학점평점
    private double minimumTotalGradePointAverage;
    // 전체학점평점
    private double totalGradePointAverage;

    public UserAcademicStatusDTO(
            int totalRequiredCredit,
            int totalCompletedCredit,
            int majorRequiredCredit,
            int majorCompletedCredit,
            int liberalRequiredCredit,
            int liberalCompletedCredit,
            int engineeringRequiredCredit,
            int engineeringCompletedCredit,
            int generalRequiredCredit,
            int generalCompletedCredit,
            double minimumTotalGradePointAverage,
            double totalGradePointAverage) {
        this.totalRequiredCredit = totalRequiredCredit;
        this.totalCompletedCredit = totalCompletedCredit;
        this.majorRequiredCredit = majorRequiredCredit;
        this.majorCompletedCredit = majorCompletedCredit;
        this.liberalRequiredCredit = liberalRequiredCredit;
        this.liberalCompletedCredit = liberalCompletedCredit;
        this.engineeringRequiredCredit = engineeringRequiredCredit;
        this.engineeringCompletedCredit = engineeringCompletedCredit;
        this.generalRequiredCredit = generalRequiredCredit;
        this.generalCompletedCredit = generalCompletedCredit;
        this.minimumTotalGradePointAverage = minimumTotalGradePointAverage;
        this.totalGradePointAverage = totalGradePointAverage;
    }

    public int getTotalRequiredCredit() {
        return totalRequiredCredit;
    }

    public int getTotalCompletedCredit() {
        return totalCompletedCredit;
    }

    public int getMajorRequiredCredit() {
        return majorRequiredCredit;
    }

    public int getMajorCompletedCredit() {
        return majorCompletedCredit;
    }

    public int getLiberalRequiredCredit() {
        return liberalRequiredCredit;
    }

    public int getLiberalCompletedCredit() {
        return liberalCompletedCredit;
    }

    public int getEngineeringRequiredCredit(){
        return engineeringRequiredCredit; 
    }

    public int getEngineeringCompletedCredit(){
        return engineeringCompletedCredit;
    }
    public int getGeneralRequiredCredit() {
        return generalRequiredCredit;
    }

    public int getGeneralCompletedCredit() {
        return generalCompletedCredit;
    }

    public double getMinimumTotalGradePointAverage() {
        return minimumTotalGradePointAverage;
    }

    public double getTotalGradePointAverage() {
        return totalGradePointAverage;
    }

    @Override
    public String toString() {
        return "UserAcademicStatusDTO{" + //
                "totalRequiredCredit=" + totalRequiredCredit + //
                ", totalCompletedCredit=" + totalCompletedCredit + //
                ", majorRequiredCredit=" + majorRequiredCredit + //
                ", majorCompletedCredit=" + majorCompletedCredit + //
                ", liberalRequiredCredit=" + liberalRequiredCredit + //
                ", liberalCompletedCredit=" + liberalCompletedCredit + //
                ", engineeringRequiredCredit=" + engineeringRequiredCredit + //
                ", engineeringCompletedCredit=" + engineeringCompletedCredit + //
                ", generalRequiredCredit=" + generalRequiredCredit + //
                ", generalCompletedCredit=" + generalCompletedCredit + //
                ", minimumTotalGradePointAverage=" + minimumTotalGradePointAverage + //
                ", totalGradePointAverage=" + totalGradePointAverage + //
                '}';
    }
}

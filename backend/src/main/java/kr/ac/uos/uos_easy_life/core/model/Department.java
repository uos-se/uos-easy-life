package kr.ac.uos.uos_easy_life.core.model;

public enum Department {
  ComputerScience(92, "컴퓨터과학부"),
  GeneralEducation(0, "교양교육부"),
  TrafficEngineering(1, "교통공학과(SMTE)"),
  IntelligentSemiconductor(2, "지능형반도체전공"),
  LegalNormStudies(3, "법규범제도학"),
  EnvironmentalHorticulture(4, "환경원예학과"),
  InternationalEducation(5, "국제교육원"),
  ElectronicElectricalComputerEngineering(6, "전자전기컴퓨터공학부"),
  GeneralBiology(7, "교양생물"),
  CollegeOfEngineering(8, "공과대학"),
  Economics(9, "경제학부"),
  Humanities(10, "인문대학"),
  UrbanRealEstatePlanningAndManagement(11, "도시부동산기획경영학전공"),
  LifelongEducation(12, "평생교육학"),
  CollegeOfArtsAndSports(13, "예술체육대학"),
  SportsScience(14, "스포츠과학과"),
  ArtificialIntelligence(15, "인공지능학과"),
  CommunicationClass(16, "의사소통교실"),
  Entrepreneurship(17, "창업학"),
  SocialScience(18, "정경대학"),
  UrbanAdministration(19, "도시행정학과"),
  Sculpture(20, "조각학과"),
  UrbanSociology(21, "도시사회학과"),
  IndustrialDesign(22, "산업디자인전공"),
  KoreanHistory(23, "국사학과"),
  MaterialsScience(24, "신소재공학과"),
  EducationDepartment(25, "교직부"),
  PublicAdministration(26, "행정학과"),
  GeneralPhysics(27, "교양물리"),
  VisualDesign(28, "시각디자인전공"),
  GeneralEnglish(29, "교양영어"),
  NaturalSciences(30, "자연과학대학"),
  GeneralMath(31, "교양수학"),
  LandscapeArchitecture(32, "조경학과"),
  GeneralComputing(33, "교양컴퓨터"),
  EastAsianCulture(34, "동아시아문화학전공"),
  FireSafetyEngineering(35, "소방방재학과"),
  GeospatialEngineering(36, "공간정보공학과"),
  BusinessAdministration(37, "경영학부"),
  Statistics(38, "통계학과"),
  ChineseCulture(39, "중국어문화학과"),
  Architecture(40, "건축학전공"),
  TaxAccounting(41, "세무학과"),
  BigDataAnalysis(42, "빅데이터분석학전공"),
  UrbanCultureContent(43, "도시문화콘텐츠학"),
  Physics(44, "물리학과"),
  GeneralPhysicalEducation(45, "교양체육"),
  LifeSciences(46, "생명과학과"),
  EnvironmentalEngineering(47, "환경공학부"),
  ArchitecturalEngineering(48, "건축공학전공"),
  InternationalRelations(49, "국제관계학과"),
  SocialWelfare(50, "사회복지학과"),
  CivilEngineering(51, "토목공학과"),
  KoreanLanguageAndLiterature(52, "국어국문학과"),
  ConvergenceBioHealth(53, "융합바이오헬스전공"),
  UrbanScience(54, "도시과학대학"),
  ChemicalEngineering(55, "화학공학과"),
  ConvergenceAppliedChemistry(56, "융합응용화학과"),
  Philosophy(57, "철학과"),
  GeneralChemistry(58, "교양화학"),
  MechanicalInformationEngineering(59, "기계정보공학과"),
  UrbanEngineering(60, "도시공학과"),
  Mathematics(61, "수학과"),
  ActuarialScience(62, "보험수리학"),
  BusinessCollege(63, "경영대학"),
  Music(64, "음악학과"),
  AdvancedArtificialIntelligence(65, "첨단인공지능전공"),
  LiberalArts(66, "자유전공학부"),
  EnglishLanguageAndLiterature(67, "영어영문학과"),
  WritingClass(68, "글쓰기교실"),
  WritingCenter(69, "글쓰기센터"),
  UrbanScienceCollege(70, "도시과학대학"),
  UrbanPlanning(71, "도시계획학전공"),
  EastAsianCultureInterdisciplinary(72, "동아시아문화연계전공");

  private final int departmentCode;
  private final String departmentName;

  Department(int departmentCode, String departmentName) {
    // 학과 코드는 0보다 크고 100보다 작아야 한다.
    // 특별히 0번은 교양교육부에 할당한다.

    if (departmentCode < 0 || departmentCode >= 100) {
      throw new IllegalArgumentException("학과 코드는 0보다 크고 100보다 작아야 합니다.");
    }
    this.departmentCode = departmentCode;
    this.departmentName = departmentName;
  }

  public static Department fromDepartmentCode(int departmentCode) {
    for (Department department : Department.values()) {
      if (department.getDepartmentCode() == departmentCode) {
        return department;
      }
    }
    return null;
  }

  public static Department fromDepartmentName(String departmentName) {
    for (Department department : Department.values()) {
      if (department.getDepartmentName().equals(departmentName)) {
        return department;
      }
    }
    return null;
  }

  public int getDepartmentCode() {
    return departmentCode;
  }

  public String getDepartmentName() {
    return departmentName;
  }
}

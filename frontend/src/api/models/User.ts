/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type User = {
    id?: string;
    name?: string;
    studentId?: string;
    department?: User.department;
    currentGrade?: number;
    currentSemester?: number;
    portalId?: string;
    hashedPortalPassword?: string;
    salt?: string;
    createdAt?: number;
    updatedAt?: number;
    password?: string;
}

export namespace User {

    export enum department {
        COMPUTER_SCIENCE = 'ComputerScience',
        GENERAL_EDUCATION = 'GeneralEducation',
        TRAFFIC_ENGINEERING = 'TrafficEngineering',
        INTELLIGENT_SEMICONDUCTOR = 'IntelligentSemiconductor',
        LEGAL_NORM_STUDIES = 'LegalNormStudies',
        ENVIRONMENTAL_HORTICULTURE = 'EnvironmentalHorticulture',
        INTERNATIONAL_EDUCATION = 'InternationalEducation',
        ELECTRONIC_ELECTRICAL_COMPUTER_ENGINEERING = 'ElectronicElectricalComputerEngineering',
        GENERAL_BIOLOGY = 'GeneralBiology',
        COLLEGE_OF_ENGINEERING = 'CollegeOfEngineering',
        ECONOMICS = 'Economics',
        HUMANITIES = 'Humanities',
        URBAN_REAL_ESTATE_PLANNING_AND_MANAGEMENT = 'UrbanRealEstatePlanningAndManagement',
        LIFELONG_EDUCATION = 'LifelongEducation',
        COLLEGE_OF_ARTS_AND_SPORTS = 'CollegeOfArtsAndSports',
        SPORTS_SCIENCE = 'SportsScience',
        ARTIFICIAL_INTELLIGENCE = 'ArtificialIntelligence',
        COMMUNICATION_CLASS = 'CommunicationClass',
        ENTREPRENEURSHIP = 'Entrepreneurship',
        SOCIAL_SCIENCE = 'SocialScience',
        URBAN_ADMINISTRATION = 'UrbanAdministration',
        SCULPTURE = 'Sculpture',
        URBAN_SOCIOLOGY = 'UrbanSociology',
        INDUSTRIAL_DESIGN = 'IndustrialDesign',
        KOREAN_HISTORY = 'KoreanHistory',
        MATERIALS_SCIENCE = 'MaterialsScience',
        EDUCATION_DEPARTMENT = 'EducationDepartment',
        PUBLIC_ADMINISTRATION = 'PublicAdministration',
        GENERAL_PHYSICS = 'GeneralPhysics',
        VISUAL_DESIGN = 'VisualDesign',
        GENERAL_ENGLISH = 'GeneralEnglish',
        NATURAL_SCIENCES = 'NaturalSciences',
        GENERAL_MATH = 'GeneralMath',
        LANDSCAPE_ARCHITECTURE = 'LandscapeArchitecture',
        GENERAL_COMPUTING = 'GeneralComputing',
        EAST_ASIAN_CULTURE = 'EastAsianCulture',
        FIRE_SAFETY_ENGINEERING = 'FireSafetyEngineering',
        GEOSPATIAL_ENGINEERING = 'GeospatialEngineering',
        BUSINESS_ADMINISTRATION = 'BusinessAdministration',
        STATISTICS = 'Statistics',
        CHINESE_CULTURE = 'ChineseCulture',
        ARCHITECTURE = 'Architecture',
        TAX_ACCOUNTING = 'TaxAccounting',
        BIG_DATA_ANALYSIS = 'BigDataAnalysis',
        URBAN_CULTURE_CONTENT = 'UrbanCultureContent',
        PHYSICS = 'Physics',
        GENERAL_PHYSICAL_EDUCATION = 'GeneralPhysicalEducation',
        LIFE_SCIENCES = 'LifeSciences',
        ENVIRONMENTAL_ENGINEERING = 'EnvironmentalEngineering',
        ARCHITECTURAL_ENGINEERING = 'ArchitecturalEngineering',
        INTERNATIONAL_RELATIONS = 'InternationalRelations',
        SOCIAL_WELFARE = 'SocialWelfare',
        CIVIL_ENGINEERING = 'CivilEngineering',
        KOREAN_LANGUAGE_AND_LITERATURE = 'KoreanLanguageAndLiterature',
        CONVERGENCE_BIO_HEALTH = 'ConvergenceBioHealth',
        URBAN_SCIENCE = 'UrbanScience',
        CHEMICAL_ENGINEERING = 'ChemicalEngineering',
        CONVERGENCE_APPLIED_CHEMISTRY = 'ConvergenceAppliedChemistry',
        PHILOSOPHY = 'Philosophy',
        GENERAL_CHEMISTRY = 'GeneralChemistry',
        MECHANICAL_INFORMATION_ENGINEERING = 'MechanicalInformationEngineering',
        URBAN_ENGINEERING = 'UrbanEngineering',
        MATHEMATICS = 'Mathematics',
        ACTUARIAL_SCIENCE = 'ActuarialScience',
        BUSINESS_COLLEGE = 'BusinessCollege',
        MUSIC = 'Music',
        ADVANCED_ARTIFICIAL_INTELLIGENCE = 'AdvancedArtificialIntelligence',
        LIBERAL_ARTS = 'LiberalArts',
        ENGLISH_LANGUAGE_AND_LITERATURE = 'EnglishLanguageAndLiterature',
    }


}

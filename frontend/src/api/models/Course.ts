/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type Course = {
    id?: string;
    lectureName?: string;
    lectureCode?: string;
    lectureCredit?: number;
    lectureGrade?: number;
    department?: Course.department;
    designCredit?: number;
    majorElective?: boolean;
    majorEssential?: boolean;
    liberalElective?: boolean;
    liberalEssential?: boolean;
    engineering?: boolean;
    basicAcademic?: boolean;
}

export namespace Course {

    export enum department {
        COMPUTER_SCIENCE = 'ComputerScience',
        GENERAL_EDUCATION = 'GeneralEducation',
    }


}

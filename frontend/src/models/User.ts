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
    }


}

/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Course } from './Course';

export type UserFullInfo = {
    name?: string;
    studentId?: string;
    email?: string;
    phone?: string;
    major?: string;
    grade?: number;
    status?: string;
    courses?: Array<Course>;
}
